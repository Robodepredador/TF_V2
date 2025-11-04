package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.OrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDistribucionRepository extends JpaRepository<OrdenDistribucion, Integer> {
}