package com.farmaceutica.programacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO que representa la información del inventario de un producto.
 * Incluye la lista de lotes disponibles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioDTO {

    private Integer idProducto;           // Id del producto
    private String nombreProducto;        // Nombre del producto
    private Integer stockTotal;           // Stock total sumando todos los lotes
    private String ubicacion;             // Ubicación física del producto
    private List<LoteDTO> lotes;          // Lista de lotes asociados a este producto
}
