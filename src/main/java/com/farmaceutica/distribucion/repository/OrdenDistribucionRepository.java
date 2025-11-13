package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.OrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdenDistribucionRepository extends JpaRepository<OrdenDistribucion, Integer>, JpaSpecificationExecutor<OrdenDistribucion> {
}