package com.farmaceutica.compras.dto;

public record SolicitudCompraDTO(
        Integer id,
        String estado,
        String fechaSolicitud,
        Integer idRequerimiento
) {}
