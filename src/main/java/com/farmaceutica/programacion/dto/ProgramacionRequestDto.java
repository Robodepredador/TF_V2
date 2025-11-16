package com.farmaceutica.programacion.dto;

import com.farmaceutica.distribucion.dto.DetalleOrdenDistribucionCreateDto;
import java.util.List;

/**
 * DTO principal para el 'fork'.
 * Contiene la decisión y el PAYLOAD (la carga) para la operación.
 */
public record ProgramacionRequestDto(
        Integer idRequerimiento, // El ID del requerimiento original (para actualizar su estado)
        String tipo, // "COMPRA" o "DISTRIBUCION"

        SolicitudCompraCreateDto solicitudCompra,
        List<DetalleOrdenDistribucionCreateDto> detallesDistribucion
) {}