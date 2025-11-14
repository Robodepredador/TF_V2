package com.farmaceutica.compras.dto;

public record RegistrarProductoProveedorDTO(
        Integer idProducto,
        Integer idProveedor,
        String precioReferencial
) {}
