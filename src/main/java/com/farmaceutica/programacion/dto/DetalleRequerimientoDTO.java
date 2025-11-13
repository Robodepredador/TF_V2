package com.farmaceutica.programacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el detalle de un requerimiento.
 * Contiene información de los productos solicitados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleRequerimientoDTO {

    private Integer idDetalle;            // Id del detalle
    private Integer idProducto;           // Id del producto solicitado
    private String nombreProducto;        // Nombre del producto
    private Integer cantidadSolicitada;   // Cantidad requerida
    private String unidadMedida;          // Unidad de medida (ej: cajas, unidades, frascos)
}
