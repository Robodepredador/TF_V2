package com.farmaceutica.distribucion.dto;

public record SeguimientoVehiculoDTO(
        Integer id,
        Integer idVehiculo,
        Integer idOrdenDistribucion,
        String estadoActual,
        String ubicacionActual,
        java.time.Instant fechaHoraActualizacion,
        String proximoDestino,
        java.time.Instant estimadoLlegada,
        String observaciones
) {}
