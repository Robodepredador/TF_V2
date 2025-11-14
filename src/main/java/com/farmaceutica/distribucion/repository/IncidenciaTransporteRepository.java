package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.IncidenciaTransporte;
import com.farmaceutica.distribucion.model.Vehiculo;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IncidenciaTransporteRepository
        extends JpaRepository<IncidenciaTransporte, Integer>, JpaSpecificationExecutor<IncidenciaTransporte> {

    List<IncidenciaTransporte> findByIdVehiculo(Vehiculo vehiculo);

    List<IncidenciaTransporte> findByIdOrdenDist(OrdenDistribucion orden);

    List<IncidenciaTransporte> findByIdDetalleDist(DetalleOrdenDistribucion detalle);

    List<IncidenciaTransporte> findByEstado(String estado);

}
