package com.farmaceutica.compras.dto;

public record DetalleOrdenCompraDTO(
        Integer id,
        Integer idProducto,
        String nombreProducto,
        Integer cantidad,
        String precioUnitario,
        String subtotal
) {}
