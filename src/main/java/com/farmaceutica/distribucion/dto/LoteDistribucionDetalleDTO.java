package com.farmaceutica.distribucion.dto;

public record LoteDistribucionDetalleDTO(
        Integer idLote,
        Integer idProducto,
        String numeroLote,
        java.time.LocalDate fechaVencimiento,
        Integer cantidadActual,
        String estado
) {}
