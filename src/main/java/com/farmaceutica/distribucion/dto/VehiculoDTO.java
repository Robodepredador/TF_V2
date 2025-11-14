package com.farmaceutica.distribucion.dto;

public record VehiculoDTO(
        Integer id,
        String placa,
        String marca,
        String modelo,
        Integer capacidad,
        String tipoVehiculo,
        String condicionesEspeciales,
        String estado
) {}
