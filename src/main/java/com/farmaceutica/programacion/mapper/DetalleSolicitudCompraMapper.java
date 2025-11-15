package com.farmaceutica.programacion.mapper;

import com.farmaceutica.programacion.dto.DetalleSolicitudCompraDto;
import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetalleSolicitudCompraMapper {
    DetalleSolicitudCompra toEntity(DetalleSolicitudCompraDto detalleSolicitudCompraDto);

    DetalleSolicitudCompraDto toDto(DetalleSolicitudCompra detalleSolicitudCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DetalleSolicitudCompra partialUpdate(DetalleSolicitudCompraDto detalleSolicitudCompraDto, @MappingTarget DetalleSolicitudCompra detalleSolicitudCompra);

    List<DetalleSolicitudCompraDto> toDto(List<DetalleSolicitudCompra> byIdSolicitudId);
}