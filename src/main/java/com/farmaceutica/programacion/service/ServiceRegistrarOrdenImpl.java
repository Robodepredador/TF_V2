// Ubicación: com/farmaceutica/programacion/service/ServiceRegistrarOrdenImpl.java

package com.farmaceutica.programacion.service;

import com.farmaceutica.almacenamiento.repository.InventarioRepository;
import com.farmaceutica.almacenamiento.repository.LoteProductoRepository;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.model.ProductoProveedor;
import com.farmaceutica.compras.repository.ProductoRepository;
import com.farmaceutica.compras.repository.ProductoProveedorRepository;
import com.farmaceutica.distribucion.dto.DetalleOrdenDistribucionCreateDto;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.repository.DetalleOrdenDistribucionRepository;
import com.farmaceutica.distribucion.repository.OrdenDistribucionRepository;
import com.farmaceutica.programacion.dto.ProgramacionRequestDto;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.model.SolicitudCompra;
import com.farmaceutica.programacion.repository.DetalleRequerimientoRepository;
import com.farmaceutica.programacion.repository.DetalleSolicitudCompraRepository;
import com.farmaceutica.programacion.repository.RequerimientoRepository;
import com.farmaceutica.programacion.repository.SolicitudCompraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // ¡Importante! Este servicio escribe en la BD
public class ServiceRegistrarOrdenImpl implements ServiceRegistrarOrden {

    // --- Repositories Inyectados ---

    // Programación
    private final RequerimientoRepository requerimientoRepository;
    private final DetalleRequerimientoRepository detalleRequerimientoRepository;
    private final SolicitudCompraRepository solicitudCompraRepository;
    private final DetalleSolicitudCompraRepository detalleSolicitudCompraRepository;

    // Distribución
    private final OrdenDistribucionRepository ordenDistribucionRepository;
    private final DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository;

    // Consulta (Almacenamiento y Compras)
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final LoteProductoRepository loteProductoRepository;
    private final ProductoProveedorRepository productoProveedorRepository;
    /**
     * Método principal (EL FORK).
     */
    @Override
    public void registrarOrdenEspecifica(ProgramacionRequestDto request) {

        Requerimiento requerimiento = requerimientoRepository.findById(request.idRequerimiento())
                .orElseThrow(() -> new EntityNotFoundException("Requerimiento no encontrado: " + request.idRequerimiento()));

        if (!"Pendiente".equals(requerimiento.getEstado())) {
            throw new IllegalStateException("El requerimiento ya fue procesado.");
        }

        if ("COMPRA".equals(request.tipo())) {
            this.registrarSolicitudDeCompra(requerimiento, request.motivoCompra());

        } else if ("DISTRIBUCION".equals(request.tipo())) {
            if (request.detallesDistribucion() == null || request.detallesDistribucion().isEmpty()) {
                throw new IllegalArgumentException("Se requieren detalles de lote para la distribución.");
            }
            this.validarStockParaDistribucion(request.detallesDistribucion());
            this.registrarOrdenDeDistribucion(requerimiento, request.detallesDistribucion());

        } else {
            throw new IllegalArgumentException("Tipo de operación no válida: " + request.tipo());
        }

        requerimiento.setEstado("En Proceso");
        requerimientoRepository.save(requerimiento);
    }

    // --- MÉTODOS PRIVADOS (HELPERS) ---

    /**
     * Mapea y guarda una nueva Solicitud de Compra.
     */
// En ServiceRegistrarOrdenImpl.java

    private void registrarSolicitudDeCompra(Requerimiento req, String motivo) {
        // 1. Crear la cabecera (SolicitudCompra) - Esto queda igual
        SolicitudCompra sc = new SolicitudCompra();
        sc.setIdRequerimiento(req);
        sc.setEstado("Pendiente");
        sc.setIdUsuarioSolicitante(req.getIdUsuarioSolicitante());
        sc.setMotivo(motivo);
        SolicitudCompra cabeceraGuardada = solicitudCompraRepository.save(sc);

        List<DetalleRequerimiento> detallesReq = detalleRequerimientoRepository.findByIdRequerimiento_Id(req.getId());

        // 2. Mapeo MANUAL (DTO -> Entidad) - Lógica de Proveedor AÑADIDA
        List<DetalleSolicitudCompra> nuevosDetallesSC = detallesReq.stream().map(detalleReq -> {
            DetalleSolicitudCompra dsc = new DetalleSolicitudCompra();
            dsc.setIdSolicitud(cabeceraGuardada);
            dsc.setIdProducto(detalleReq.getIdProducto());
            dsc.setCantidadSolicitada(detalleReq.getCantidad());
            dsc.setIdDetalleRequerimiento(detalleReq.getId());
            dsc.setEstado("Pendiente");

            // --- INICIO DE LA LÓGICA DE PROVEEDOR ---
            // Buscamos una entrada en la tabla ProductoProveedor para este producto
            ProductoProveedor prodProv = productoProveedorRepository
                    .findFirstByIdProducto_Id(detalleReq.getIdProducto().getId())
                    .orElse(null); // .findFirst... trae el primero que encuentre

            if (prodProv != null) {
                // Si lo encontramos, asignamos el proveedor y el precio
                dsc.setIdProveedorSeleccionado(prodProv.getIdProveedor());
                dsc.setPrecioReferencial(prodProv.getPrecioReferencial());
            } else {
                // ¡ERROR DE NEGOCIO!
                // No se puede crear la solicitud porque no hay proveedores
                // para este producto, y tu BD lo requiere (NOT NULL).
                throw new EntityNotFoundException("No se encontró un proveedor por defecto para el producto: " + detalleReq.getIdProducto().getNombreProducto());

                // (Si 'idProveedorSeleccionado' permitiera nulos, podríamos omitir esta parte)
            }
            // --- FIN DE LA LÓGICA DE PROVEEDOR ---

            return dsc;
        }).collect(Collectors.toList());

        detalleSolicitudCompraRepository.saveAll(nuevosDetallesSC);
    }

    /**
     * Mapea y guarda una nueva Orden de Distribución.
     */
    private void registrarOrdenDeDistribucion(Requerimiento req, List<DetalleOrdenDistribucionCreateDto> detallesDto) {
        // 1. Crear la cabecera (OrdenDistribucion)
        OrdenDistribucion od = new OrdenDistribucion();
        od.setIdRequerimiento(req.getId()); // Guardamos el ID para trazabilidad
        od.setEstado("Pendiente"); // Pasa a Almacén como "Pendiente"
        od.setIdUsuarioCreacion(req.getIdUsuarioSolicitante());
        od.setPrioridad(req.getPrioridad());

        OrdenDistribucion cabeceraGuardada = ordenDistribucionRepository.save(od);

        // 2. Mapeo MANUAL (DTO -> Entidad)
        List<DetalleOrdenDistribucion> nuevosDetallesOD = detallesDto.stream().map(dto -> {
            DetalleOrdenDistribucion dod = new DetalleOrdenDistribucion();
            dod.setIdOrdenDist(cabeceraGuardada); // Enlaza al padre

            // "Resolvemos" los IDs del DTO a Entidades reales
            Producto producto = productoRepository.findById(dto.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + dto.idProducto()));
            dod.setIdProducto(producto);

            dod.setIdLote(loteProductoRepository.findById(dto.idLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado: " + dto.idLote())));

            dod.setCantidad(dto.cantidad());
            dod.setEstadoEntrega("Pendiente");
            dod.setCondicionesTransporte(producto.getCondicionesTransporte()); // Hereda del producto

            return dod;
        }).collect(Collectors.toList());

        detalleOrdenDistribucionRepository.saveAll(nuevosDetallesOD);
    }

    /**
     * Validación simple de stock.
     */
    private void validarStockParaDistribucion(List<DetalleOrdenDistribucionCreateDto> detallesDto) {
        // Aquí puedes añadir una lógica de validación más robusta.
        // Por ejemplo, verificar que el dto.idLote realmente pertenezca a dto.idProducto
        // y que el stock en Inventario para ESE lote sea suficiente.

        for (DetalleOrdenDistribucionCreateDto dto : detallesDto) {
            Integer stockEnLote = inventarioRepository.findStockTotalByIdProducto(dto.idProducto());
            if (stockEnLote == null || stockEnLote < dto.cantidad()) {
                throw new IllegalStateException("Stock insuficiente para el producto ID: " + dto.idProducto());
            }
        }
    }
}