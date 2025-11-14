package com.farmaceutica.compras.service;

import com.farmaceutica.compras.dto.*;
import com.farmaceutica.compras.mapper.ComprasMapper;
import com.farmaceutica.compras.model.*;
import com.farmaceutica.compras.repository.*;
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.SolicitudCompra;
import com.farmaceutica.programacion.repository.DetalleSolicitudCompraRepository;
import com.farmaceutica.programacion.repository.SolicitudCompraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ServiceRegistrarOrdenCompra {

    private final SolicitudCompraRepository solicitudCompraRepository;
    private final DetalleSolicitudCompraRepository detalleSolicitudCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final ProductoProveedorRepository productoProveedorRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleOrdenCompraRepository;

    public ServiceRegistrarOrdenCompra(
            SolicitudCompraRepository solicitudCompraRepository,
            DetalleSolicitudCompraRepository detalleSolicitudCompraRepository,
            ProveedorRepository proveedorRepository,
            ProductoRepository productoRepository,
            ProductoProveedorRepository productoProveedorRepository,
            OrdenCompraRepository ordenCompraRepository,
            DetalleOrdenCompraRepository detalleOrdenCompraRepository) {

        this.solicitudCompraRepository = solicitudCompraRepository;
        this.detalleSolicitudCompraRepository = detalleSolicitudCompraRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.productoProveedorRepository = productoProveedorRepository;
        this.ordenCompraRepository = ordenCompraRepository;
        this.detalleOrdenCompraRepository = detalleOrdenCompraRepository;
    }

    /* ----------------------------------------------------
     * CONSULTAR SOLICITUD DE COMPRA
     * ---------------------------------------------------- */
    public SolicitudCompraDTO consultarSolicitud(Integer idSolicitud) {
        SolicitudCompra solicitud = solicitudCompraRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud de compra no encontrada."));

        return ComprasMapper.toSolicitudCompraDTO(solicitud);
    }


    /* ----------------------------------------------------
     * CONSULTAR DETALLE DE SOLICITUD
     * ---------------------------------------------------- */
    public List<DetalleSolicitudCompraDTO> consultarDetalleSolicitud(Integer idSolicitud) {
        SolicitudCompra solicitud = solicitudCompraRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada."));

        List<DetalleSolicitudCompra> detalles =
                detalleSolicitudCompraRepository.findByIdSolicitud(solicitud);

        return ComprasMapper.toDetalleSolicitudList(detalles);
    }


    /* ----------------------------------------------------
     * LISTAR PRODUCTOS
     * ---------------------------------------------------- */
    public List<ProductoDTO> listarProductos() {
        return ComprasMapper.toProductoList(productoRepository.findByEstadoTrue());
    }


    /* ----------------------------------------------------
     * ACTUALIZAR PROVEEDOR EN DETALLE SOLICITUD
     * ---------------------------------------------------- */
    public void actualizarProveedorDetalleSolicitud(Integer idDetalle, Integer idProveedor) {

        DetalleSolicitudCompra detalle = detalleSolicitudCompraRepository.findById(idDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle de solicitud no encontrado."));

        Proveedor proveedor = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        detalle.setIdProveedorSeleccionado(proveedor);
        detalle.setFechaActualizacion(Instant.now());

        detalleSolicitudCompraRepository.save(detalle);
    }


    /* ----------------------------------------------------
     * CONSULTAR PRODUCTO–PROVEEDOR
     * ---------------------------------------------------- */
    public List<ProductoProveedorDTO> consultarProductoProveedor(Integer idProducto) {

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        return ComprasMapper.toProductoProveedorList(
                productoProveedorRepository.findByIdProducto(producto)
        );
    }


    /* ----------------------------------------------------
     * REGISTRAR PRODUCTO–PROVEEDOR (cotización)
     * ---------------------------------------------------- */
    public void registrarProductoProveedor(RegistrarProductoProveedorDTO dto) {

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Proveedor proveedor = proveedorRepository.findById(dto.idProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        // Validar si ya existe
        if (productoProveedorRepository.existsByIdProductoAndIdProveedor(producto, proveedor)) {
            throw new RuntimeException("La relación Producto–Proveedor ya existe.");
        }

        ProductoProveedor pp = new ProductoProveedor();
        pp.setIdProducto(producto);
        pp.setIdProveedor(proveedor);
        pp.setPrecioReferencial(new BigDecimal(dto.precioReferencial()));
        pp.setEstado(true);
        pp.setFechaCreacion(Instant.now());
        pp.setFechaActualizacion(Instant.now());

        productoProveedorRepository.save(pp);
    }


    /* ----------------------------------------------------
     * REGISTRAR ORDEN DE COMPRA COMPLETA
     * ---------------------------------------------------- */
    public OrdenCompraResponseDTO registrarOrden(RegistrarOrdenCompraRequestDTO dto) {

        SolicitudCompra solicitud = solicitudCompraRepository.findById(dto.idSolicitud())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada."));

        if (!solicitud.getEstado().equals("Pendiente")) {
            throw new RuntimeException("La solicitud ya fue procesada anteriormente.");
        }

        Proveedor proveedor = proveedorRepository.findById(dto.idProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        // Crear OC
        OrdenCompra oc = new OrdenCompra();
        oc.setFechaOrden(LocalDate.now());
        oc.setNumeroOrden(dto.numeroOrden());
        oc.setEstado("Emitida");
        oc.setIdSolicitud(solicitud);
        oc.setIdProveedor(proveedor.getId());
        oc.setFechaEntregaEstimada(parseDate(dto.fechaEntregaEstimada()));
        oc.setObservaciones(dto.observaciones());
        oc.setFechaCreacion(Instant.now());
        oc.setFechaActualizacion(Instant.now());

        ordenCompraRepository.save(oc);

        BigDecimal subtotalTotal = BigDecimal.ZERO;

        // Registrar detalles
        for (RegistrarDetalleOrdenCompraDTO det : dto.detalles()) {

            Producto producto = productoRepository.findById(det.idProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

            DetalleOrdenCompra detalle = new DetalleOrdenCompra();
            detalle.setIdOrden(oc);
            detalle.setIdProducto(producto);
            detalle.setCantidad(det.cantidad());

            BigDecimal precio = new BigDecimal(det.precioUnitario());
            detalle.setPrecioUnitario(precio);

            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(det.cantidad()));
            detalle.setSubtotal(subtotal);

            detalle.setEstado("Pendiente");
            detalle.setFechaCreacion(Instant.now());
            detalle.setFechaActualizacion(Instant.now());

            detalleOrdenCompraRepository.save(detalle);

            subtotalTotal = subtotalTotal.add(subtotal);
        }

        // Calcular IGV y total
        BigDecimal igv = subtotalTotal.multiply(BigDecimal.valueOf(0.18));
        BigDecimal total = subtotalTotal.add(igv);

        oc.setSubtotal(subtotalTotal);
        oc.setIgv(igv);
        oc.setTotal(total);
        oc.setFechaActualizacion(Instant.now());

        ordenCompraRepository.save(oc);

        // Actualizar estado solicitud
        solicitud.setEstado("Procesada");
        solicitud.setFechaActualizacion(Instant.now());
        solicitudCompraRepository.save(solicitud);

        return ComprasMapper.toOrdenCompraResponseDTO(oc, proveedor.getNombreProveedor());
    }


    /* ----------------------------------------------------
     * ACTUALIZAR COSTOS DE LA ORDEN DE COMPRA
     * ---------------------------------------------------- */
    public void actualizarCostos(ActualizarCostosOCRequestDTO dto) {

        OrdenCompra oc = ordenCompraRepository.findById(dto.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada."));

        oc.setSubtotal(new BigDecimal(dto.subtotal()));
        oc.setIgv(new BigDecimal(dto.igv()));
        oc.setTotal(new BigDecimal(dto.total()));
        oc.setFechaActualizacion(Instant.now());

        ordenCompraRepository.save(oc);
    }


    /* ----------------------------------------------------
     * UTILS
     * ---------------------------------------------------- */
    private LocalDate parseDate(String s) {
        return (s == null || s.isEmpty()) ? null : LocalDate.parse(s);
    }

}
