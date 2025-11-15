package com.farmaceutica.almacenamiento.mapper;

import com.farmaceutica.almacenamiento.dto.IncidenciaLoteDto;
import com.farmaceutica.almacenamiento.model.IncidenciaLote;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LoteProductoMapper.class})
public interface IncidenciaLoteMapper {
    @Mapping(source = "idUsuarioRegistroNombreUsuario", target = "idUsuarioRegistro.nombreUsuario")
    IncidenciaLote toEntity(IncidenciaLoteDto incidenciaLoteDto);

    @Mapping(source = "idUsuarioRegistro.nombreUsuario", target = "idUsuarioRegistroNombreUsuario")
    IncidenciaLoteDto toDto(IncidenciaLote incidenciaLote);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idUsuarioRegistroNombreUsuario", target = "idUsuarioRegistro.nombreUsuario")
    IncidenciaLote partialUpdate(IncidenciaLoteDto incidenciaLoteDto, @MappingTarget IncidenciaLote incidenciaLote);
}