package com.farmaceutica.compras.dto;

public record ActualizarCostosOCRequestDTO(
        Integer idOrden,
        String subtotal,
        String igv,
        String total
) {}
