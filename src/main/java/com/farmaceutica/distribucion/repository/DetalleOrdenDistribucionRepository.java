package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenDistribucionRepository extends JpaRepository<DetalleOrdenDistribucion, Integer> {
}