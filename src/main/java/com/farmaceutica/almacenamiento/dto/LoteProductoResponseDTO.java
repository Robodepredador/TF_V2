package com.farmaceutica.almacenamiento.dto;

import java.time.LocalDate;

public record LoteProductoResponseDTO(
        Integer id,
        Integer idProducto,
        String nombreProducto,
        Integer idOrdenCompra,
        String numeroLote,
        LocalDate fechaFabricacion,
        LocalDate fechaVencimiento,
        Integer cantidadInicial,
        Integer cantidadActual,
        String estado,
        String ubicacionAlmacen
) {}

