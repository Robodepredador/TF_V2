package com.farmaceutica.distribucion.dto;

public record RegistrarVehiculoRequestDTO(
        String placa,
        String marca,
        String modelo,
        Integer capacidad,
        String tipoVehiculo,
        String condicionesEspeciales
) {}
