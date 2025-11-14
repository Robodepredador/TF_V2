package com.farmaceutica.compras.dto;

public record DetalleSolicitudCompraDTO(
        Integer id,
        Integer idProducto,
        String nombreProducto,
        Integer cantidadSolicitada,
        Integer cantidadAprobada,
        Integer idProveedorSeleccionado
) {}
