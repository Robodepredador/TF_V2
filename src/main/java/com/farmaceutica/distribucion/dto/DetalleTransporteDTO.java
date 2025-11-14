package com.farmaceutica.distribucion.dto;

public record DetalleTransporteDTO(
        Integer id,
        Integer idSeguimiento,
        Integer idDetalleDistribucion,
        Integer idLote,
        Integer cantidadTransportada,
        Boolean condicionesVerificadas,
        java.math.BigDecimal temperaturaRegistrada,
        String observacionesEntrega
) {}
