package com.farmaceutica.programacion.dto;

import java.math.BigDecimal;

public record DetalleSolicitudCompraCreateDto(
        Integer idProducto,
        Integer idDetalleRequerimiento, // Para trazar de dónde vino
        Integer cantidadSolicitada,
        Integer idProveedorSeleccionado, // Tu entidad lo marcó como NOT NULL
        BigDecimal precioReferencial
) {}