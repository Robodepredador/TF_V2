package com.farmaceutica.distribucion.dto;

public record OrdenDistribucionDetalleResponseDTO(
        Integer id,
        String estado,
        String prioridad,
        String observaciones,
        String fechaDistribucion,
        java.util.List<DetalleOrdenDistribucionDTO> detalles
) {}
