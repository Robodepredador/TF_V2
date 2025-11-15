package com.farmaceutica.almacenamiento.mapper;

import com.farmaceutica.almacenamiento.dto.InventarioStockDto;
import com.farmaceutica.almacenamiento.model.Inventario;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventarioMapper {
    Inventario toEntity(InventarioStockDto inventarioStockDto);

    InventarioStockDto toDto(Inventario inventario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Inventario partialUpdate(InventarioStockDto inventarioStockDto, @MappingTarget Inventario inventario);

    List<InventarioStockDto> toInventarioStockDto(List<Inventario> inventario);
}