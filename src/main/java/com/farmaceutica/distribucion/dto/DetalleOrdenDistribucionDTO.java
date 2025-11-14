package com.farmaceutica.distribucion.dto;

public record DetalleOrdenDistribucionDTO(
        Integer id,
        Integer idOrdenDist,
        Integer idLote,
        Integer idProducto,
        Integer cantidad,
        String condicionesTransporte,
        String temperaturaRequerida,
        Integer idVehiculoAsignado,
        String estadoEntrega,
        String observaciones
) {}

