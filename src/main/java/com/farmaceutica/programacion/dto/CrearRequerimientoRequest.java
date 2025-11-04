package com.farmaceutica.programacion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data; // o @Getter, @Setter
import java.time.LocalDate;
import java.util.List;

// Este DTO representa el JSON que el frontend enviará
@Data
public class CrearRequerimientoRequest {

    @NotNull
    private Integer idDepartamento;

    @NotNull
    private Integer idUsuarioSolicitante; // (Más adelante, esto se tomará del token de seguridad)

    @NotNull
    private String prioridad;

    private LocalDate fechaLimite;
    private String observacion;

    @NotEmpty // No se puede crear un requerimiento sin items
    private List<ItemRequerimientoDto> items;


    // Sub-clase para los items
    @Data
    public static class ItemRequerimientoDto {
        @NotNull
        private Integer idProducto;

        @NotNull
        @Positive(message = "La cantidad debe ser mayor a 0")
        private Integer cantidad;
    }
}