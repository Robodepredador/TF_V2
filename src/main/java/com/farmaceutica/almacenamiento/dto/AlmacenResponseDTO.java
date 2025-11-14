package com.farmaceutica.almacenamiento.dto;

public record AlmacenResponseDTO(
        Integer id,
        String nombre,
        String ubicacion,
        String tipo,
        Integer capacidad,
        Boolean estado
) {}
