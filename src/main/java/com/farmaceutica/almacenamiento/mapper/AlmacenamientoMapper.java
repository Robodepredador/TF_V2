package com.farmaceutica.almacenamiento.mapper;

import com.farmaceutica.almacenamiento.model.*;
import com.farmaceutica.almacenamiento.dto.*;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.model.OrdenCompra;
import com.farmaceutica.core.model.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlmacenamientoMapper {

    // ======================================================
    //                ALMACÉN MAPPER
    // ======================================================
    @Mapping(target = "nombre", source = "nombreAlmacen")
    @Mapping(target = "tipo", source = "tipoAlmacen")
    AlmacenResponseDTO toAlmacenResponseDTO(Almacen almacen);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombreAlmacen", source = "nombre")
    @Mapping(target = "tipoAlmacen", source = "tipo")
    @Mapping(target = "estado", constant = "true")
    Almacen toAlmacenEntity(AlmacenRequestDTO dto);


    // ======================================================
    //                INVENTARIO MAPPER
    // ======================================================
    @Mapping(target = "idAlmacen", source = "idAlmacen.id")
    @Mapping(target = "nombreAlmacen", source = "idAlmacen.nombreAlmacen")
    @Mapping(target = "idLote", source = "idLote.id")
    @Mapping(target = "numeroLote", source = "idLote.numeroLote")
    InventarioResponseDTO toInventarioResponseDTO(Inventario inventario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInventarioFromDTO(InventarioUpdateRequestDTO dto, @MappingTarget Inventario inventario);


    // ======================================================
    //             LOTE PRODUCTO MAPPER
    // ======================================================
    @Mapping(target = "idProducto", source = "idProducto.id")
    @Mapping(target = "nombreProducto", source = "idProducto.nombreProducto")
    @Mapping(target = "idOrdenCompra", source = "idOrdenCompra.id")
    LoteProductoResponseDTO toLoteResponseDTO(LoteProducto lote);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idProducto", source = "producto")
    @Mapping(target = "idOrdenCompra", source = "ordenCompra")
    @Mapping(target = "cantidadActual", source = "cantidadInicial") // al registrar lote
    @Mapping(target = "estado", constant = "Disponible")
    LoteProducto toLoteEntity(
            RegistrarLoteRequestDTO dto,
            @Context Producto producto,
            @Context OrdenCompra ordenCompra
    );


    // ======================================================
    //            MOVIMIENTO INVENTARIO MAPPER
    // ======================================================
    @Mapping(target = "idInventario", source = "idInventario.id")
    @Mapping(target = "idUsuarioRegistro", source = "idUsuarioRegistro.id")
    MovimientoInventarioResponseDTO toMovimientoResponseDTO(MovimientoInventario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idInventario", source = "inventario")
    @Mapping(target = "idUsuarioRegistro", source = "usuario")
    MovimientoInventario toMovimientoEntity(
            RegistrarMovimientoInventarioRequestDTO dto,
            @Context Inventario inventario,
            @Context Usuario usuario
    );


    // ======================================================
    //              INCIDENCIA LOTE MAPPER
    // ======================================================
    @Mapping(target = "idLote", source = "idLote.id")
    @Mapping(target = "numeroLote", source = "idLote.numeroLote")
    @Mapping(target = "idOrdenCompra", source = "idOrdenCompra.id")
    @Mapping(target = "idUsuarioRegistro", source = "idUsuarioRegistro.id")
    IncidenciaLoteResponseDTO toIncidenciaResponseDTO(IncidenciaLote entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idLote", source = "lote")
    @Mapping(target = "idOrdenCompra", source = "ordenCompra")
    @Mapping(target = "idUsuarioRegistro", source = "usuario")
    @Mapping(target = "estado", constant = "Pendiente")
    IncidenciaLote toIncidenciaEntity(
            RegistrarIncidenciaLoteRequestDTO dto,
            @Context LoteProducto lote,
            @Context OrdenCompra ordenCompra,
            @Context Usuario usuario
    );

}
