package com.farmaceutica.compras.dto;

import java.util.List;

public record RegistrarOrdenCompraRequestDTO(
        Integer idSolicitud,
        Integer idProveedor,
        String numeroOrden,
        String fechaEntregaEstimada,
        String observaciones,
        List<RegistrarDetalleOrdenCompraDTO> detalles
) {}
