package com.farmaceutica.programacion.service;

import com.farmaceutica.almacenamiento.model.LoteProducto;
import com.farmaceutica.almacenamiento.repository.InventarioRepository;
import com.farmaceutica.almacenamiento.repository.LoteProductoRepository;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.repository.ProductoRepository;
import com.farmaceutica.distribucion.dto.DetalleOrdenDistribucionCreateDto;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.repository.DetalleOrdenDistribucionRepository;
import com.farmaceutica.distribucion.repository.OrdenDistribucionRepository;
import com.farmaceutica.exception.BusinessException;
import com.farmaceutica.programacion.dto.ProgramacionRequestDto;
import com.farmaceutica.programacion.dto.ProgramacionResultadoDto;
import com.farmaceutica.programacion.dto.SolicitudCompraCreateDto;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.repository.DetalleRequerimientoRepository;
import com.farmaceutica.programacion.model.SolicitudCompra;
import com.farmaceutica.programacion.repository.DetalleSolicitudCompraRepository;
import com.farmaceutica.programacion.repository.RequerimientoRepository;
import com.farmaceutica.programacion.repository.SolicitudCompraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceRegistrarOrdenImpl implements ServiceRegistrarOrden {

    private final RequerimientoRepository requerimientoRepository;
    private final SolicitudCompraRepository solicitudCompraRepository;
    private final DetalleSolicitudCompraRepository detalleSolicitudCompraRepository;
    private final OrdenDistribucionRepository ordenDistribucionRepository;
    private final DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository;
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final LoteProductoRepository loteProductoRepository;
    private final DetalleRequerimientoRepository detalleRequerimientoRepository;


    @Override
    public ProgramacionResultadoDto registrarOrdenEspecifica(ProgramacionRequestDto request) {

        if (request == null) {
            throw new BusinessException("La petición es nula.");
        }

        // Cargar requerimiento
        Requerimiento req = cargarYValidarRequerimientoPendiente(request.idRequerimiento());

        String tipo = request.tipo();
        Integer idSolicitud = null;
        Integer idOrden = null;

        // === COMPRA ===
        if ("COMPRA".equalsIgnoreCase(tipo)) {

            if (request.solicitudCompra() == null) {
                throw new BusinessException("Para COMPRA debe enviar 'solicitudCompra'.");
            }

            idSolicitud = procesarCompra(req, request.solicitudCompra());

            marcarRequerimientoEnProceso(req);

            return new ProgramacionResultadoDto(
                    "COMPRA", idSolicitud, null, "Solicitud de compra creada con éxito."
            );
        }

        // === DISTRIBUCIÓN ===
        if ("DISTRIBUCION".equalsIgnoreCase(tipo)) {

            if (request.detallesDistribucion() == null || request.detallesDistribucion().isEmpty()) {
                throw new BusinessException("Para DISTRIBUCION debe enviar 'detallesDistribucion'.");
            }

            validarStockParaDistribucion(request.detallesDistribucion());

            idOrden = procesarDistribucion(req, request.detallesDistribucion());

            // 👇 Ahora SÍ se marca En Proceso
            marcarRequerimientoEnProceso(req);

            return new ProgramacionResultadoDto(
                    "DISTRIBUCION", null, idOrden, "Orden de distribución creada con éxito."
            );
        }

        throw new BusinessException("Tipo no soportado: " + tipo);
    }


    // -------------------------------------------------------
    // PROCESAR COMPRA
    // -------------------------------------------------------
    private Integer procesarCompra(Requerimiento req, SolicitudCompraCreateDto dto) {

        if (dto.detalles() == null || dto.detalles().isEmpty())
            throw new BusinessException("La solicitud de compra debe incluir al menos un detalle.");

        dto.detalles().forEach(det -> {
            if (det.cantidadSolicitada() == null || det.cantidadSolicitada() <= 0)
                throw new BusinessException("Cantidad inválida para producto: " + det.idProducto());
        });

        SolicitudCompra sc = new SolicitudCompra();
        sc.setIdRequerimiento(req);
        sc.setIdUsuarioSolicitante(dto.idUsuarioSolicitante());
        sc.setMotivo(dto.motivo());
        sc.setEstado("Pendiente");

        SolicitudCompra cabecera = solicitudCompraRepository.save(sc);

        List<DetalleSolicitudCompra> detalles = dto.detalles().stream().map(detDto -> {

            DetalleSolicitudCompra d = new DetalleSolicitudCompra();
            d.setIdSolicitud(cabecera);
            d.setIdProducto(productoRepository.findById(detDto.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + detDto.idProducto())));
            d.setIdDetalleRequerimiento(detDto.idDetalleRequerimiento());
            d.setCantidadSolicitada(detDto.cantidadSolicitada());
            d.setCantidadAprobada(0);
            d.setEstado("Pendiente");
            return d;

        }).collect(Collectors.toList());

        detalleSolicitudCompraRepository.saveAll(detalles);

        return cabecera.getId();
    }

    // -------------------------------------------------------
    // PROCESAR DISTRIBUCIÓN
    // -------------------------------------------------------
    private Integer procesarDistribucion(Requerimiento req, List<DetalleOrdenDistribucionCreateDto> detallesDto) {

        OrdenDistribucion od = new OrdenDistribucion();
        od.setIdRequerimiento(req.getId());
        od.setIdUsuarioCreacion(req.getIdUsuarioSolicitante());
        od.setPrioridad(req.getPrioridad());
        od.setEstado("Pendiente");

        OrdenDistribucion cabecera = ordenDistribucionRepository.save(od);

        List<DetalleOrdenDistribucion> detalles = detallesDto.stream().map(dto -> {

            if (dto.cantidad() == null || dto.cantidad() <= 0)
                throw new BusinessException("Cantidad inválida para producto: " + dto.idProducto());

            DetalleOrdenDistribucion dod = new DetalleOrdenDistribucion();
            dod.setIdOrdenDist(cabecera);

            Producto producto = productoRepository.findById(dto.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + dto.idProducto()));
            dod.setIdProducto(producto);

            var lote = loteProductoRepository.findById(dto.idLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado: " + dto.idLote()));

            if (!Objects.equals(lote.getIdProducto().getId(), dto.idProducto()))
                throw new BusinessException("El lote " + dto.idLote() + " no pertenece al producto " + dto.idProducto());

            dod.setIdLote(lote);
            dod.setCantidad(dto.cantidad());
            dod.setEstadoEntrega("Pendiente");
            dod.setCondicionesTransporte(producto.getCondicionesTransporte());

            return dod;

        }).collect(Collectors.toList());

        detalleOrdenDistribucionRepository.saveAll(detalles);

        return cabecera.getId();
    }

    // -------------------------------------------------------
    // VALIDACIONES
    // -------------------------------------------------------
    private Requerimiento cargarYValidarRequerimientoPendiente(Integer idRequerimiento) {

        if (idRequerimiento == null)
            throw new BusinessException("Debe enviar idRequerimiento.");

        Requerimiento req = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new EntityNotFoundException("Requerimiento no encontrado."));

        if (!"Pendiente".equalsIgnoreCase(req.getEstado()))
            throw new BusinessException("El requerimiento no está Pendiente.");

        return req;
    }

    private void marcarRequerimientoEnProceso(Requerimiento req) {
        req.setEstado("En Proceso");
        requerimientoRepository.save(req);
    }

    private void validarStockParaDistribucion(List<DetalleOrdenDistribucionCreateDto> detallesDto) {

        for (DetalleOrdenDistribucionCreateDto dto : detallesDto) {

            if (dto.cantidad() == null || dto.cantidad() <= 0)
                throw new BusinessException("Cantidad inválida para producto: " + dto.idProducto());

            Integer stockTotal = inventarioRepository.findStockTotalByIdProducto(dto.idProducto());

            if (stockTotal == null || stockTotal < dto.cantidad())
                throw new BusinessException("Stock insuficiente para producto: " + dto.idProducto());
        }
    }

    @Override
    @Transactional
    public ProgramacionResultadoDto procesarAutomatico(Integer idRequerimiento) {

        // 1. Cargar requerimiento
        Requerimiento req = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new EntityNotFoundException("Requerimiento no encontrado."));

        // 2. Validar estado
        if (!"Pendiente".equalsIgnoreCase(req.getEstado())) {
            throw new BusinessException("El requerimiento no está Pendiente.");
        }

        // 3. Cargar detalles
        List<DetalleRequerimiento> detalles = detalleRequerimientoRepository.findByIdRequerimiento_Id(idRequerimiento);

        if (detalles.isEmpty()) {
            throw new BusinessException("El requerimiento no tiene detalles.");
        }

        Integer idSolicitudCompra = null;
        Integer idOrdenDistribucion = null;

        List<DetalleSolicitudCompra> detallesCompra = new java.util.ArrayList<>();
        List<DetalleOrdenDistribucion> detallesDistribucion = new java.util.ArrayList<>();

        // 4. Procesar cada producto del requerimiento
        for (DetalleRequerimiento det : detalles) {

            Integer productoId = det.getIdProducto().getId();
            Integer cantidadSolicitada = det.getCantidad();

            // stock total real del inventario
            Integer stockTotal = inventarioRepository.findStockTotalByIdProducto(productoId);
            stockTotal = (stockTotal == null ? 0 : stockTotal);

            // === DISTRIBUCIÓN (stock suficiente) ===
            if (stockTotal >= cantidadSolicitada) {

                // buscar lotes con stock > 0 y del producto, ordenados FEFO
                List<LoteProducto> lotesFEFO = inventarioRepository.findByIdLote_IdProducto_IdAndStockActualGreaterThan(productoId, 0)
                        .stream()
                        .map(inv -> inv.getIdLote())
                        .distinct()
                        .sorted(Comparator.comparing(LoteProducto::getFechaVencimiento))
                        .toList();

                if (lotesFEFO.isEmpty()) {
                    throw new BusinessException("No hay lotes disponibles para el producto " + productoId);
                }

                // Tomar el lote FEFO
                LoteProducto loteSeleccionado = lotesFEFO.get(0);

                DetalleOrdenDistribucion d = new DetalleOrdenDistribucion();
                d.setIdProducto(det.getIdProducto());
                d.setIdLote(loteSeleccionado);
                d.setCantidad(cantidadSolicitada);
                d.setEstadoEntrega("Pendiente");
                d.setCondicionesTransporte(det.getIdProducto().getCondicionesTransporte());

                detallesDistribucion.add(d);

            } else {
                // === COMPRA (stock insuficiente) ===
                DetalleSolicitudCompra d = new DetalleSolicitudCompra();
                d.setIdProducto(det.getIdProducto());
                d.setIdDetalleRequerimiento(det.getId());
                d.setCantidadSolicitada(cantidadSolicitada);
                d.setCantidadAprobada(0);
                d.setEstado("Pendiente");

                detallesCompra.add(d);
            }
        }

        // 5. Crear Orden de Distribución
        if (!detallesDistribucion.isEmpty()) {

            OrdenDistribucion od = new OrdenDistribucion();
            od.setIdRequerimiento(req.getId());
            od.setIdUsuarioCreacion(req.getIdUsuarioSolicitante());
            od.setPrioridad(req.getPrioridad());
            od.setEstado("Pendiente");

            OrdenDistribucion saved = ordenDistribucionRepository.save(od);

            for (DetalleOrdenDistribucion d : detallesDistribucion) {
                d.setIdOrdenDist(saved);
            }

            detalleOrdenDistribucionRepository.saveAll(detallesDistribucion);

            idOrdenDistribucion = saved.getId();
        }

        // 6. Crear Solicitud de Compra
        if (!detallesCompra.isEmpty()) {

            SolicitudCompra sc = new SolicitudCompra();
            sc.setIdRequerimiento(req);
            sc.setIdUsuarioSolicitante(req.getIdUsuarioSolicitante());
            sc.setEstado("Pendiente");
            sc.setMotivo("Generado automáticamente por falta de stock");

            SolicitudCompra saved = solicitudCompraRepository.save(sc);

            for (DetalleSolicitudCompra d : detallesCompra) {
                d.setIdSolicitud(saved);
            }

            detalleSolicitudCompraRepository.saveAll(detallesCompra);

            idSolicitudCompra = saved.getId();
        }

        // 7. Actualizar estado del requerimiento
        req.setEstado("En Proceso");
        requerimientoRepository.save(req);

        // 8. Respuesta final
        return new ProgramacionResultadoDto(
                "AUTO",
                idSolicitudCompra,
                idOrdenDistribucion,
                "Requerimiento atendido automáticamente."
        );
    }




}
