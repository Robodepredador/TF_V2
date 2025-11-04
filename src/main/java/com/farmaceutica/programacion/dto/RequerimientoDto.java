package com.farmaceutica.programacion.dto;

import lombok.Data;
import java.time.LocalDate;

// Este DTO representa la respuesta JSON que tu API devolverá
@Data
public class RequerimientoDto {
    private Integer id;
    private LocalDate fechaSolicitud;
    private String nombreDepartamento; // (Traemos el nombre, no el ID)
    private String prioridad;
    private String estado;
    private Integer totalItems;
}