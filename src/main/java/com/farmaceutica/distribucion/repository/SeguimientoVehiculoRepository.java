package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.SeguimientoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeguimientoVehiculoRepository extends JpaRepository<SeguimientoVehiculo, Integer>, JpaSpecificationExecutor<SeguimientoVehiculo> {
}