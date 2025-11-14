package com.farmaceutica.almacenamiento.dto;

import java.time.Instant;

public record IncidenciaLoteResponseDTO(
        Integer id,
        Integer idLote,
        String numeroLote,
        Integer idOrdenCompra,
        String tipoIncidencia,
        String descripcion,
        String nivelSeveridad,
        String estado,
        Instant fechaIncidencia,
        Instant fechaResolucion,
        String observaciones,
        Integer idUsuarioRegistro
) {}

