package com.farmaceutica.compras.mapper;

import com.farmaceutica.compras.dto.ProductoProveedorDto;
import com.farmaceutica.compras.model.ProductoProveedor;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoProveedorMapper {
    ProductoProveedor toEntity(ProductoProveedorDto productoProveedorDto);

    ProductoProveedorDto toDto(ProductoProveedor productoProveedor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductoProveedor partialUpdate(ProductoProveedorDto productoProveedorDto, @MappingTarget ProductoProveedor productoProveedor);

    List<ProductoProveedorDto> toDto(List<ProductoProveedor> byIdProductoId);
}