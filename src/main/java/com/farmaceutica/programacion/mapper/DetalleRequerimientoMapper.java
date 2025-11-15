package com.farmaceutica.programacion.mapper;

import com.farmaceutica.programacion.dto.DetalleRequerimientoDto;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetalleRequerimientoMapper {
    @Mapping(source = "idProducto.idFormaNombreForma", target = "idProducto.idForma.nombreForma")
    @Mapping(source = "idProducto.idTipoNombreTipo", target = "idProducto.idTipo.nombreTipo")
    DetalleRequerimiento toEntity(DetalleRequerimientoDto detalleRequerimientoDto);

    @InheritInverseConfiguration(name = "toEntity")
    DetalleRequerimientoDto toDto(DetalleRequerimiento detalleRequerimiento);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DetalleRequerimiento partialUpdate(DetalleRequerimientoDto detalleRequerimientoDto, @MappingTarget DetalleRequerimiento detalleRequerimiento);

    List<DetalleRequerimientoDto> toDetalleRequerimientoDto(List<DetalleRequerimiento> detalles);
}