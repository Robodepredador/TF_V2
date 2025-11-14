package com.farmaceutica.compras.dto;

public record RegistrarDetalleOrdenCompraDTO(
        Integer idProducto,
        Integer cantidad,
        String precioUnitario
) {}
