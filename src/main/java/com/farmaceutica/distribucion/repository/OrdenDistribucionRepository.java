package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.OrdenDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrdenDistribucionRepository
        extends JpaRepository<OrdenDistribucion, Integer>, JpaSpecificationExecutor<OrdenDistribucion> {

    List<OrdenDistribucion> findByEstado(String estado);

    List<OrdenDistribucion> findByPrioridad(String prioridad);

}
