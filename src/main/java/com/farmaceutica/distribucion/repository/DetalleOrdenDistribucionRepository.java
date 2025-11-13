package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetalleOrdenDistribucionRepository extends JpaRepository<DetalleOrdenDistribucion, Integer>, JpaSpecificationExecutor<DetalleOrdenDistribucion> {
}