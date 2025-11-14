package com.farmaceutica.compras.dto;

public record ProveedorResponseDTO(
        Integer id,
        String nombre,
        String ruc,
        String telefono,
        String correo,
        Boolean estado
) {}
