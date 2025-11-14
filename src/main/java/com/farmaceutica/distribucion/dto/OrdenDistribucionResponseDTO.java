package com.farmaceutica.distribucion.dto;

public record OrdenDistribucionResponseDTO(
        Integer id,
        String estado,
        String prioridad,
        String observaciones,
        String fechaDistribucion
) {}
