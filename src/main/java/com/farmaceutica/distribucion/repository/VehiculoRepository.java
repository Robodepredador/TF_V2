package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer>, JpaSpecificationExecutor<Vehiculo> {
}