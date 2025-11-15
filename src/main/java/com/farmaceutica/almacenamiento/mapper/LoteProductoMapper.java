package com.farmaceutica.almacenamiento.mapper;

import com.farmaceutica.almacenamiento.dto.LoteProductoDto;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoteProductoMapper {
    LoteProducto toEntity(LoteProductoDto loteProductoDto);

    LoteProductoDto toDto(LoteProducto loteProducto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LoteProducto partialUpdate(LoteProductoDto loteProductoDto, @MappingTarget LoteProducto loteProducto);
}