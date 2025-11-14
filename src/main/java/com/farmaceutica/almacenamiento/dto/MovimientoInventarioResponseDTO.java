package com.farmaceutica.almacenamiento.dto;

import java.time.Instant;

public record MovimientoInventarioResponseDTO(
        Integer id,
        Integer idInventario,
        String tipoMovimiento,
        Integer cantidad,
        Instant fechaMovimiento,
        Integer idUsuarioRegistro,
        String tipoReferencia,
        Integer idReferencia,
        String observacion
) {}

