// Ubicación: com/farmaceutica/distribucion/dto/OrdenDistribucionCreateDto.java

package com.farmaceutica.distribucion.dto;

import java.util.List;

/**
 * DTO para crear la cabecera de una Orden de Distribución.
 * Representa el "Request Body" principal.
 */
public record OrdenDistribucionCreateDto(
        Integer idRequerimiento,
        Integer idUsuarioCreacion,
        String prioridad,
        List<DetalleOrdenDistribucionCreateDto> detalles
) {}