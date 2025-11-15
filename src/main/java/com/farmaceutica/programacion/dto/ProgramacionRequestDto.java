// Ubicación: com/farmaceutica/programacion/dto/ProgramacionRequestDto.java

package com.farmaceutica.programacion.dto;

import com.farmaceutica.distribucion.dto.DetalleOrdenDistribucionCreateDto;
import java.util.List;

/**
 * DTO principal para el 'fork' del ServiceRegistrarOrden.
 * Contiene la decisión del supervisor y los detalles necesarios.
 */
public record ProgramacionRequestDto(
        Integer idRequerimiento,
        String tipo, // "COMPRA" o "DISTRIBUCION"
        String motivoCompra, // Opcional, solo si es "COMPRA"

        // Solo si el tipo es "DISTRIBUCION"
        List<DetalleOrdenDistribucionCreateDto> detallesDistribucion
) {}