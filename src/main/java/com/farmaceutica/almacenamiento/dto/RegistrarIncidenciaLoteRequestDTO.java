package com.farmaceutica.almacenamiento.dto;

public record RegistrarIncidenciaLoteRequestDTO(
        Integer idLote,
        Integer idOrdenCompra,
        String tipoIncidencia,
        String descripcion,
        String nivelSeveridad,
        String observaciones,
        Integer idUsuarioRegistro
) {}

