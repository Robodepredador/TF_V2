package com.farmaceutica.distribucion.dto;

public record OrdenDistribucionDTO(
        Integer id,
        String estado,
        String prioridad,
        String observaciones
) {}
