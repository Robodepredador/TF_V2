package com.farmaceutica.distribucion.dto;

public record RegistrarIncidenciaTransporteRequestDTO(
        Integer idVehiculo,
        Integer idOrdenDist,
        Integer idDetalleDist,
        String tipoIncidencia,
        String descripcion,
        String impacto
) {}
