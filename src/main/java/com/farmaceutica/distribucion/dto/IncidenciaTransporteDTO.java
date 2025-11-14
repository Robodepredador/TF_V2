package com.farmaceutica.distribucion.dto;

public record IncidenciaTransporteDTO(
        Integer id,
        Integer idVehiculo,
        Integer idOrdenDist,
        Integer idDetalleDist,
        String tipoIncidencia,
        String descripcion,
        String impacto,
        String estado,
        java.time.Instant fechaIncidencia,
        java.time.Instant fechaResolucion,
        Integer idUsuarioReporta,
        String observaciones
) {}
