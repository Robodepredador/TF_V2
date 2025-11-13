package com.farmaceutica.programacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * DTO que representa la información de un lote del inventario.
 * Muestra los datos relevantes para el programador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteDTO {

    private Integer idLote;               // Id del lote
    private Integer idInventario;         // Inventario al que pertenece
    private Integer cantidad;             // Cantidad de unidades en el lote
    private LocalDate fechaIngreso;       // Fecha en que se registró el lote
    private LocalDate fechaVencimiento;   // Fecha de vencimiento del producto
    private String estado;                // Estado actual (Activo, Vencido, etc.)
}
