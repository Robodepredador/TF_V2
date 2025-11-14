package com.farmaceutica.almacenamiento.dto;

public record InventarioResponseDTO(
        Integer id,
        Integer idAlmacen,
        String nombreAlmacen,
        Integer idLote,
        String numeroLote,
        Integer stockActual,
        Integer stockMinimo,
        Integer stockMaximo,
        String ubicacionEspecifica
) {}
