package com.farmaceutica.distribucion.dto;

public record RegistrarOrdenDistribucionRequestDTO(
        Integer idRequerimiento,
        Integer idUsuarioCreacion,
        String prioridad,
        String observaciones
) {}

