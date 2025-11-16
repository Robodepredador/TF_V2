// Ubicación: com/farmaceutica/programacion/service/ServiceRegistrarOrdenImpl.java

package com.farmaceutica.programacion.service;

import com.farmaceutica.almacenamiento.repository.InventarioRepository;
import com.farmaceutica.almacenamiento.repository.LoteProductoRepository;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.repository.ProductoRepository;
import com.farmaceutica.compras.repository.ProveedorRepository; // <-- Importante
// import com.farmaceutica.compras.repository.ProductoProveedorRepository; // <-- ELIMINADO
import com.farmaceutica.distribucion.dto.DetalleOrdenDistribucionCreateDto;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.repository.DetalleOrdenDistribucionRepository;
import com.farmaceutica.distribucion.repository.OrdenDistribucionRepository;
import com.farmaceutica.programacion.dto.ProgramacionRequestDto;
import com.farmaceutica.programacion.dto.SolicitudCompraCreateDto; // <-- Importante
// import com.farmaceutica.programacion.model.DetalleRequerimiento; // <-- ELIMINADO
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.model.SolicitudCompra;
// import com.farmaceutica.programacion.repository.DetalleRequerimientoRepository; // <-- ELIMINADO
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
@Transactional
public class ServiceRegistrarOrdenImpl implements ServiceRegistrarOrden {

    // --- Repositories Inyectados (LIMPIOS) ---
    private final RequerimientoRepository requerimientoRepository;
    // private final DetalleRequerimientoRepository detalleRequerimientoRepository; // <-- ELIMINADO
    private final SolicitudCompraRepository solicitudCompraRepository;
    private final DetalleSolicitudCompraRepository detalleSolicitudCompraRepository;
    private final OrdenDistribucionRepository ordenDistribucionRepository;
    private final DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final LoteProductoRepository loteProductoRepository;
    private final ProveedorRepository proveedorRepository; // <-- Necesario para el mapeo
    // private final ProductoProveedorRepository productoProveedorRepository; // <-- ELIMINADO


    @Override
    public void registrarOrdenEspecifica(ProgramacionRequestDto request) {

        Requerimiento requerimiento = requerimientoRepository.findById(request.idRequerimiento())
                .orElseThrow(() -> new EntityNotFoundException("Requerimiento no encontrado"));

        if (!"Pendiente".equals(requerimiento.getEstado())) {
            throw new IllegalStateException("El requerimiento ya fue procesado.");
        }

        // ---------- EL FORK (ACTUALIZADO) ----------

        if ("COMPRA".equals(request.tipo())) {
            // Verificamos que el payload de compra exista
            if (request.solicitudCompra() == null) {
                throw new IllegalArgumentException("Se requiere el payload 'solicitudCompra' para la operación COMPRA.");
            }
            // Llama al método privado con el DTO de creación
            this.registrarSolicitudDeCompra(requerimiento, request.solicitudCompra());

        } else if ("DISTRIBUCION".equals(request.tipo())) {
            // (La lógica de distribución sigue igual)
            if (request.detallesDistribucion() == null || request.detallesDistribucion().isEmpty()) {
                throw new IllegalArgumentException("Se requieren detalles de lote para la distribución.");
            }
            this.validarStockParaDistribucion(request.detallesDistribucion());
            this.registrarOrdenDeDistribucion(requerimiento, request.detallesDistribucion());

        } else {
            throw new IllegalArgumentException("Tipo de operación no válida: " + request.tipo());
        }

        // Actualizar el estado del requerimiento original
        requerimiento.setEstado("En Proceso");
        requerimientoRepository.save(requerimiento);
    }

    /**
     * Mapea y guarda una nueva Solicitud de Compra (Versión B).
     * AHORA SÍ USA LOS DTOs DE CREACIÓN.
     */
    private void registrarSolicitudDeCompra(Requerimiento req, SolicitudCompraCreateDto dto) {

        // 1. Crear la cabecera (SolicitudCompra)
        SolicitudCompra sc = new SolicitudCompra();
        sc.setIdRequerimiento(req); // Se enlaza al requerimiento original
        sc.setIdUsuarioSolicitante(dto.idUsuarioSolicitante());
        sc.setMotivo(dto.motivo());
        sc.setEstado("Pendiente");

        SolicitudCompra cabeceraGuardada = solicitudCompraRepository.save(sc);

        // 2. Mapeo MANUAL de Detalles (DTO -> Entidad)
        List<DetalleSolicitudCompra> nuevosDetallesSC = dto.detalles().stream().map(detalleDto -> {
            DetalleSolicitudCompra dsc = new DetalleSolicitudCompra();
            dsc.setIdSolicitud(cabeceraGuardada); // Enlaza al padre

            // "Resuelve" los IDs del DTO buscando en la BD
            dsc.setIdProducto(productoRepository.findById(detalleDto.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + detalleDto.idProducto())));

            // Lógica corregida: usa el ProveedorRepository
            dsc.setIdProveedorSeleccionado(proveedorRepository.findById(detalleDto.idProveedorSeleccionado())
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado: " + detalleDto.idProveedorSeleccionado())));

            dsc.setIdDetalleRequerimiento(detalleDto.idDetalleRequerimiento()); // Trazabilidad
            dsc.setCantidadSolicitada(detalleDto.cantidadSolicitada());
            dsc.setPrecioReferencial(detalleDto.precioReferencial());
            dsc.setEstado("Pendiente");

            return dsc;
        }).collect(Collectors.toList());

        detalleSolicitudCompraRepository.saveAll(nuevosDetallesSC);
    }

    // ... (El resto de métodos: registrarOrdenDeDistribucion, validarStock, etc. quedan igual) ...

    private void registrarOrdenDeDistribucion(Requerimiento req, List<DetalleOrdenDistribucionCreateDto> detallesDto) {
        OrdenDistribucion od = new OrdenDistribucion();
        od.setIdRequerimiento(req.getId());
        od.setEstado("Pendiente");
        od.setIdUsuarioCreacion(req.getIdUsuarioSolicitante());
        od.setPrioridad(req.getPrioridad());

        OrdenDistribucion cabeceraGuardada = ordenDistribucionRepository.save(od);

        List<DetalleOrdenDistribucion> nuevosDetallesOD = detallesDto.stream().map(dto -> {
            DetalleOrdenDistribucion dod = new DetalleOrdenDistribucion();
            dod.setIdOrdenDist(cabeceraGuardada);

            Producto producto = productoRepository.findById(dto.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + dto.idProducto()));
            dod.setIdProducto(producto);

            dod.setIdLote(loteProductoRepository.findById(dto.idLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado: " + dto.idLote())));

            dod.setCantidad(dto.cantidad());
            dod.setEstadoEntrega("Pendiente");
            dod.setCondicionesTransporte(producto.getCondicionesTransporte());

            return dod;
        }).collect(Collectors.toList());

        detalleOrdenDistribucionRepository.saveAll(nuevosDetallesOD);
    }

    private void validarStockParaDistribucion(List<DetalleOrdenDistribucionCreateDto> detallesDto) {
        for (DetalleOrdenDistribucionCreateDto dto : detallesDto) {
            Integer stockEnLote = inventarioRepository.findStockTotalByIdProducto(dto.idProducto());
            if (stockEnLote == null || stockEnLote < dto.cantidad()) {
                throw new IllegalStateException("Stock insuficiente para el producto ID: " + dto.idProducto());
            }
        }
    }
}