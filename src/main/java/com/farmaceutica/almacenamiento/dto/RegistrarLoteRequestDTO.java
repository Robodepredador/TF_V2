package com.farmaceutica.almacenamiento.dto;

import java.time.LocalDate;

public record RegistrarLoteRequestDTO(
        Integer idProducto,
        Integer idOrdenCompra,
        String numeroLote,
        LocalDate fechaFabricacion,
        LocalDate fechaVencimiento,
        Integer cantidadInicial,
        String ubicacionAlmacen,
        String temperaturaAlmacenamiento
) {}

