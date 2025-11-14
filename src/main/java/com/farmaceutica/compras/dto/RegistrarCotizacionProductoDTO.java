package com.farmaceutica.compras.dto;

public record RegistrarCotizacionProductoDTO(
        Integer idProducto,
        Integer idProveedor,
        String precioCotizado
) {}
