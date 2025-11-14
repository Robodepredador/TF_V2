package com.farmaceutica.distribucion.dto;

public record RegistrarDetalleOrdenDistribucionRequestDTO(
        Integer idOrdenDist,
        Integer idLote,
        Integer idProducto,
        Integer cantidad,
        String condicionesTransporte,
        String temperaturaRequerida
) {}
