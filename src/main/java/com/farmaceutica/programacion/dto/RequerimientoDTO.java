package com.farmaceutica.programacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO que representa la información general de un requerimiento.
 * Se usa para listar los requerimientos pendientes o en proceso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequerimientoDTO {

    private Integer idRequerimiento;      // Id del requerimiento
    private String areaSolicitante;       // Área o departamento que realizó la solicitud
    private String prioridad;             // Nivel de prioridad del requerimiento
    private String estado;                // Estado actual (Pendiente, Atendido, etc.)
    private LocalDateTime fechaCreacion;  // Fecha en que se generó el requerimiento
}
