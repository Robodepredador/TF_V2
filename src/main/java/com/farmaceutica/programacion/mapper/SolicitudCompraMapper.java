package com.farmaceutica.programacion.mapper;

import com.farmaceutica.programacion.dto.SolicitudCompraDto;
import com.farmaceutica.programacion.model.SolicitudCompra;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SolicitudCompraMapper {
    @Mapping(source = "idRequerimientoId", target = "idRequerimiento.id")
    SolicitudCompra toEntity(SolicitudCompraDto solicitudCompraDto);

    @Mapping(source = "idRequerimiento.id", target = "idRequerimientoId")
    SolicitudCompraDto toDto(SolicitudCompra solicitudCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idRequerimientoId", target = "idRequerimiento.id")
    SolicitudCompra partialUpdate(SolicitudCompraDto solicitudCompraDto, @MappingTarget SolicitudCompra solicitudCompra);

    List<SolicitudCompraDto> toDto(List<SolicitudCompra> byEstado);
}