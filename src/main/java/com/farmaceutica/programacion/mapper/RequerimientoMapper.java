package com.farmaceutica.programacion.mapper;

import com.farmaceutica.programacion.dto.RequerimientoResumenDto;
import com.farmaceutica.programacion.model.Requerimiento;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequerimientoMapper {
    @Mapping(source = "idDepartamentoNombreDepartamento", target = "idDepartamento.nombreDepartamento")
    Requerimiento toEntity(RequerimientoResumenDto requerimientoResumenDto);

    @Mapping(source = "idDepartamento.nombreDepartamento", target = "idDepartamentoNombreDepartamento")
    RequerimientoResumenDto toDto(Requerimiento requerimiento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "idDepartamentoNombreDepartamento", target = "idDepartamento.nombreDepartamento")
    Requerimiento partialUpdate(RequerimientoResumenDto requerimientoResumenDto, @MappingTarget Requerimiento requerimiento);

    List<RequerimientoResumenDto> toRequerimientoResumenDto(List<Requerimiento> requerimientos);
}