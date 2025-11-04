package com.farmaceutica.programacion.mapper;

// DTOs
import com.farmaceutica.programacion.dto.CrearRequerimientoRequest;
import com.farmaceutica.programacion.dto.RequerimientoDto;

// Entidades
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.core.model.Departamento;
import com.farmaceutica.core.model.Producto;

import java.time.LocalDate;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class RequerimientoMapper {

    /**
     * Convierte el DTO de Creación a la Entidad Requerimiento (Cabecera)
     * Necesita el objeto 'Departamento' que el servicio buscará.
     */
    public static Requerimiento toEntity(CrearRequerimientoRequest dto, Departamento depto) {
        Requerimiento req = new Requerimiento();

        req.setDepartamento(depto);
        req.setIdUsuarioSolicitante(dto.getIdUsuarioSolicitante());
        req.setPrioridad(dto.getPrioridad());
        req.setFechaLimite(dto.getFechaLimite());
        req.setObservacion(dto.getObservacion());

        // Valores por defecto
        req.setEstado("Pendiente");
        req.setFechaSolicitud(LocalDate.now());
        req.setFechaCreacion(Instant.now());
        req.setFechaActualizacion(Instant.now());

        return req;
    }

    /**
     * Convierte un Item del DTO al DetalleRequerimiento (Entidad)
     * Necesita la cabecera 'Requerimiento' y el 'Producto' que el servicio buscará.
     */
    public static DetalleRequerimiento itemToDetalle(
            CrearRequerimientoRequest.ItemRequerimientoDto itemDto,
            Requerimiento reqGuardado,
            Producto prod) {

        DetalleRequerimiento detalle = new DetalleRequerimiento();
        detalle.setRequerimiento(reqGuardado);
        detalle.setProducto(prod);
        detalle.setCantidad(itemDto.getCantidad());

        // Valores por defecto
        detalle.setCantidadAtendida(0);
        detalle.setFechaCreacion(Instant.now());
        detalle.setFechaActualizacion(Instant.now());

        return detalle;
    }

    /**
     * Convierte la Entidad Requerimiento a un DTO de respuesta
     */
    public static RequerimientoDto toDto(Requerimiento req, int totalItems) {
        RequerimientoDto dto = new RequerimientoDto();
        dto.setId(req.getId());
        dto.setFechaSolicitud(req.getFechaSolicitud());
        dto.setPrioridad(req.getPrioridad());
        dto.setEstado(req.getEstado());

        if (req.getDepartamento() != null) {
            dto.setNombreDepartamento(req.getDepartamento().getNombreDepartamento());
        }

        dto.setTotalItems(totalItems);
        return dto;
    }
}