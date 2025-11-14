package com.farmaceutica.compras.dto;

public record ProductoProveedorDTO(
        Integer idProdProv,
        Integer idProducto,
        Integer idProveedor,
        String nombreProveedor,
        String nombreProducto,
        String registroSanitario,
        String codigoDigemid,
        String formaFarmaceutica,
        String tipoProducto,
        String condicionesAlmacenamiento,
        String condicionesTransporte,
        String precioReferencial
) {}
