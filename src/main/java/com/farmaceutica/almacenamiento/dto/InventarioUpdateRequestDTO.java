package com.farmaceutica.almacenamiento.dto;

public record InventarioUpdateRequestDTO(
        Integer idInventario,
        Integer stockActual,
        Integer stockMinimo,
        Integer stockMaximo,
        String ubicacionEspecifica
) {}
