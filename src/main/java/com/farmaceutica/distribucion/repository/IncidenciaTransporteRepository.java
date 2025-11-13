package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.IncidenciaTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncidenciaTransporteRepository extends JpaRepository<IncidenciaTransporte, Integer>, JpaSpecificationExecutor<IncidenciaTransporte> {
}