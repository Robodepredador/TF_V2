package com.farmaceutica.almacenamiento.dto;

public record RegistrarMovimientoInventarioRequestDTO(
        Integer idInventario,
        String tipoMovimiento,
        Integer cantidad,
        Integer idUsuarioRegistro,
        String tipoReferencia,
        Integer idReferencia,
        String observacion
) {}
