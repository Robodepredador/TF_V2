package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.SeguimientoVehiculo;
import com.farmaceutica.distribucion.model.Vehiculo;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SeguimientoVehiculoRepository
        extends JpaRepository<SeguimientoVehiculo, Integer>, JpaSpecificationExecutor<SeguimientoVehiculo> {

    List<SeguimientoVehiculo> findByIdVehiculo(Vehiculo vehiculo);

    List<SeguimientoVehiculo> findByIdOrdenDist(OrdenDistribucion orden);

    List<SeguimientoVehiculo> findByEstadoActual(String estado);

}
