package com.farmaceutica.compras.dto;

public record OrdenCompraResponseDTO(
        Integer id,
        String numeroOrden,
        String estado,
        String fechaOrden,
        String fechaEntregaEstimada,
        String proveedor,
        String total
) {}
