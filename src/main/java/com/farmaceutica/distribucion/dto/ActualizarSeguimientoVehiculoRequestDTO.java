package com.farmaceutica.distribucion.dto;

public record ActualizarSeguimientoVehiculoRequestDTO(
        Integer idVehiculo,
        String estadoActual,
        String ubicacionActual,
        String proximoDestino
) {}
