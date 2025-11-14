package com.farmaceutica.compras.dto;

public record ProveedorRequestDTO(
        String nombre,
        String ruc,
        String direccion,
        String telefono,
        String correo
) {}
