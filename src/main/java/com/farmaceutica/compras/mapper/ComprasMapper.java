package com.farmaceutica.compras.mapper;

import com.farmaceutica.compras.dto.*;
import com.farmaceutica.compras.model.*;
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.SolicitudCompra;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ComprasMapper {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    /* ----------------------------------------------------
     *  PROVEEDORES
     * ---------------------------------------------------- */

    public static ProveedorResponseDTO toProveedorResponseDTO(Proveedor proveedor) {
        if (proveedor == null) return null;

        return new ProveedorResponseDTO(
                proveedor.getId(),
                proveedor.getNombreProveedor(),
                proveedor.getRuc(),
                proveedor.getTelefono(),
                proveedor.getCorreo(),
                proveedor.getEstado()
        );
    }

    public static List<ProveedorListDTO> toProveedorList(List<Proveedor> proveedores) {
        return proveedores.stream()
                .map(p -> new ProveedorListDTO(
                        p.getId(),
                        p.getNombreProveedor()
                ))
                .collect(Collectors.toList());
    }


    /* ----------------------------------------------------
     *  PRODUCTOS
     * ---------------------------------------------------- */

    public static ProductoDTO toProductoDTO(Producto p) {
        if (p == null) return null;

        return new ProductoDTO(
                p.getId(),
                p.getNombreProducto(),
                p.getDescripcionProducto(),
                p.getCodigoDigemid(),
                p.getRegistroSanitario(),
                p.getIdTipo() != null ? p.getIdTipo().getNombreTipo() : null,
                p.getIdForma() != null ? p.getIdForma().getNombreForma() : null
        );
    }

    public static List<ProductoDTO> toProductoList(List<Producto> productos) {
        return productos.stream()
                .map(ComprasMapper::toProductoDTO)
                .collect(Collectors.toList());
    }


    /* ----------------------------------------------------
     *  PRODUCTO - PROVEEDOR
     * ---------------------------------------------------- */

    public static ProductoProveedorDTO toProductoProveedorDTO(ProductoProveedor pp) {
        if (pp == null) return null;

        Producto producto = pp.getIdProducto();
        Proveedor proveedor = pp.getIdProveedor();

        return new ProductoProveedorDTO(
                pp.getId(),
                producto.getId(),
                proveedor.getId(),
                proveedor.getNombreProveedor(),
                producto.getNombreProducto(),
                producto.getRegistroSanitario(),
                producto.getCodigoDigemid(),
                producto.getIdForma() != null ? producto.getIdForma().getNombreForma() : null,
                producto.getIdTipo() != null ? producto.getIdTipo().getNombreTipo() : null,
                producto.getCondicionesAlmacenamiento(),
                producto.getCondicionesTransporte(),
                formatBigDecimal(pp.getPrecioReferencial())
        );
    }

    public static List<ProductoProveedorDTO> toProductoProveedorList(List<ProductoProveedor> lista) {
        return lista.stream()
                .map(ComprasMapper::toProductoProveedorDTO)
                .collect(Collectors.toList());
    }


    /* ----------------------------------------------------
     *  SOLICITUD DE COMPRA
     * ---------------------------------------------------- */

    public static SolicitudCompraDTO toSolicitudCompraDTO(SolicitudCompra s) {
        if (s == null) return null;

        return new SolicitudCompraDTO(
                s.getId(),
                s.getEstado(),
                s.getFechaSolicitud() != null ? s.getFechaSolicitud().toString() : null,
                s.getIdRequerimiento() != null ? s.getIdRequerimiento().getId() : null
        );
    }

    public static DetalleSolicitudCompraDTO toDetalleSolicitudCompraDTO(DetalleSolicitudCompra d) {
        if (d == null) return null;

        return new DetalleSolicitudCompraDTO(
                d.getId(),
                d.getIdProducto().getId(),
                d.getIdProducto().getNombreProducto(),
                d.getCantidadSolicitada(),
                d.getCantidadAprobada(),
                d.getIdProveedorSeleccionado().getId()
        );
    }

    public static List<DetalleSolicitudCompraDTO> toDetalleSolicitudList(List<DetalleSolicitudCompra> detalles) {
        return detalles.stream()
                .map(ComprasMapper::toDetalleSolicitudCompraDTO)
                .collect(Collectors.toList());
    }


    /* ----------------------------------------------------
     *  ORDEN DE COMPRA
     * ---------------------------------------------------- */

    public static OrdenCompraResponseDTO toOrdenCompraResponseDTO(OrdenCompra oc, String proveedorNombre) {
        if (oc == null) return null;

        return new OrdenCompraResponseDTO(
                oc.getId(),
                oc.getNumeroOrden(),
                oc.getEstado(),
                oc.getFechaOrden() != null ? oc.getFechaOrden().toString() : null,
                oc.getFechaEntregaEstimada() != null ? oc.getFechaEntregaEstimada().toString() : null,
                proveedorNombre,
                oc.getTotal() != null ? formatBigDecimal(oc.getTotal()) : "0.00"
        );
    }

    public static DetalleOrdenCompraDTO toDetalleOrdenCompraDTO(DetalleOrdenCompra d) {
        if (d == null) return null;

        return new DetalleOrdenCompraDTO(
                d.getId(),
                d.getIdProducto().getId(),
                d.getIdProducto().getNombreProducto(),
                d.getCantidad(),
                formatBigDecimal(d.getPrecioUnitario()),
                formatBigDecimal(d.getSubtotal())
        );
    }

    public static List<DetalleOrdenCompraDTO> toDetalleOrdenList(List<DetalleOrdenCompra> detalles) {
        return detalles.stream()
                .map(ComprasMapper::toDetalleOrdenCompraDTO)
                .collect(Collectors.toList());
    }


    /* ----------------------------------------------------
     *  UTILITARIOS
     * ---------------------------------------------------- */

    private static String formatBigDecimal(BigDecimal value) {
        return value != null ? value.setScale(2, BigDecimal.ROUND_HALF_UP).toString() : null;
    }
}
