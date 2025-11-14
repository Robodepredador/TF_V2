package com.farmaceutica.distribucion.mapper;

import com.farmaceutica.distribucion.model.*;
import com.farmaceutica.distribucion.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DistribucionMapper {

    DistribucionMapper INSTANCE = Mappers.getMapper(DistribucionMapper.class);

    // -------------------------------------------------------------
    // ORDEN DE DISTRIBUCIÓN
    // -------------------------------------------------------------
    @Mapping(target = "id", source = "entity.id")
    OrdenDistribucionDTO toOrdenDistribucionDTO(OrdenDistribucion entity);

    @Mapping(target = "fechaDistribucion", source = "entity.fechaDistribucion")
    OrdenDistribucionResponseDTO toOrdenDistribucionResponseDTO(OrdenDistribucion entity);


    @Mapping(target = "detalles", source = "detalleOrdenDistribucions")
    OrdenDistribucionDetalleResponseDTO toOrdenDistribucionDetalleResponseDTO(
            OrdenDistribucion entity,
            List<DetalleOrdenDistribucionDTO> detallesDTO);


    List<OrdenDistribucionDTO> toOrdenDistribucionDTOList(List<OrdenDistribucion> list);


    // -------------------------------------------------------------
    // DETALLE ORDEN DISTRIBUCIÓN
    // -------------------------------------------------------------
    @Mapping(target = "idOrdenDist", source = "idOrdenDist.id")
    @Mapping(target = "idLote", source = "idLote.id")
    @Mapping(target = "idProducto", source = "idProducto.id")
    @Mapping(target = "idVehiculoAsignado", source = "idVehiculoAsignado.id")
    DetalleOrdenDistribucionDTO toDetalleOrdenDistribucionDTO(DetalleOrdenDistribucion entity);


    List<DetalleOrdenDistribucionDTO> toDetalleOrdenDistribucionDTOList(List<DetalleOrdenDistribucion> list);


    // -------------------------------------------------------------
    // LOTE DETALLE PARA DISTRIBUCIÓN
    // -------------------------------------------------------------
    @Mapping(target = "idLote", source = "id")
    @Mapping(target = "idProducto", source = "idProducto.id")
    @Mapping(target = "numeroLote", source = "numeroLote")
    @Mapping(target = "fechaVencimiento", source = "fechaVencimiento")
    @Mapping(target = "cantidadActual", source = "cantidadActual")
    @Mapping(target = "estado", source = "estado")
    LoteDistribucionDetalleDTO toLoteDistribucionDetalleDTO(com.farmaceutica.almacenamiento.model.LoteProducto entity);


    // -------------------------------------------------------------
    // VEHÍCULO
    // -------------------------------------------------------------
    @Mapping(target = "id", source = "entity.id")
    VehiculoDTO toVehiculoDTO(Vehiculo entity);

    VehiculoResponseDTO toVehiculoResponseDTO(Vehiculo entity);

    List<VehiculoDTO> toVehiculoDTOList(List<Vehiculo> list);


    // -------------------------------------------------------------
    // SEGUIMIENTO VEHICULAR
    // -------------------------------------------------------------
    @Mapping(target = "idVehiculo", source = "idVehiculo.id")
    @Mapping(target = "idOrdenDistribucion", source = "idOrdenDist.id")
    SeguimientoVehiculoDTO toSeguimientoVehiculoDTO(SeguimientoVehiculo entity);


    List<SeguimientoVehiculoDTO> toSeguimientoVehiculoDTOList(List<SeguimientoVehiculo> list);


    // -------------------------------------------------------------
    // DETALLE TRANSPORTE
    // -------------------------------------------------------------
    @Mapping(target = "idSeguimiento", source = "idSeguimiento.id")
    @Mapping(target = "idDetalleDistribucion", source = "idDetalleDistribucion.id")
    @Mapping(target = "idLote", source = "idLote.id")
    DetalleTransporteDTO toDetalleTransporteDTO(DetalleTransporte entity);


    List<DetalleTransporteDTO> toDetalleTransporteDTOList(List<DetalleTransporte> list);


    // -------------------------------------------------------------
    // INCIDENCIA TRANSPORTE
    // -------------------------------------------------------------
    @Mapping(target = "idVehiculo", source = "idVehiculo.id")
    @Mapping(target = "idOrdenDist", source = "idOrdenDist.id")
    @Mapping(target = "idDetalleDist", source = "idDetalleDist.id")
    @Mapping(target = "idUsuarioReporta", source = "idUsuarioReporta.id")
    IncidenciaTransporteDTO toIncidenciaTransporteDTO(IncidenciaTransporte entity);


    List<IncidenciaTransporteDTO> toIncidenciaTransporteDTOList(List<IncidenciaTransporte> list);

}
