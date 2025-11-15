package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenDistribucionRepository extends JpaRepository<DetalleOrdenDistribucion, Integer> {
}