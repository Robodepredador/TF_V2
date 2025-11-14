package com.farmaceutica.distribucion.dto;

public record ActualizarEstadoOrdenDistribucionRequestDTO(
        Integer idOrdenDistribucion,
        String nuevoEstado
) {}

