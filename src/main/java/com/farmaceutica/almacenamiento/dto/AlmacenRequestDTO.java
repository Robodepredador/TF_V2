package com.farmaceutica.almacenamiento.dto;

public record AlmacenRequestDTO(
        String nombre,
        String ubicacion,
        String tipo,
        Integer capacidad
) {}
