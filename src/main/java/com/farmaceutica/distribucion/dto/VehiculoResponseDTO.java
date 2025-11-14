package com.farmaceutica.distribucion.dto;

public record VehiculoResponseDTO(
        Integer id,
        String placa,
        String marca,
        String modelo,
        Integer capacidad,
        String tipoVehiculo,
        String estado,
        String fechaActualizacion
) {}
