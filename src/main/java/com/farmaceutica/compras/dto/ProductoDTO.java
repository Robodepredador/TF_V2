package com.farmaceutica.compras.dto;

public record ProductoDTO(
        Integer id,
        String nombre,
        String descripcion,
        String codigoDigemid,
        String registroSanitario,
        String tipo,
        String forma
) {}
