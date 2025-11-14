package com.farmaceutica.distribucion.dto;

public record RegistrarDetalleTransporteRequestDTO(
        Integer idSeguimiento,
        Integer idDetalleDistribucion,
        Integer idLote,
        Integer cantidadTransportada,
        Boolean condicionesVerificadas,
        java.math.BigDecimal temperaturaRegistrada,
        String observacionesEntrega
) {}
