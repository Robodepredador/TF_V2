package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleTransporte;
import com.farmaceutica.distribucion.model.SeguimientoVehiculo;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DetalleTransporteRepository
        extends JpaRepository<DetalleTransporte, Integer>, JpaSpecificationExecutor<DetalleTransporte> {

    List<DetalleTransporte> findByIdSeguimiento(SeguimientoVehiculo seguimiento);

    List<DetalleTransporte> findByIdDetalleDistribucion(DetalleOrdenDistribucion detalle);

    List<DetalleTransporte> findByIdLote(LoteProducto lote);

}
