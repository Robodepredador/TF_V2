package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetalleTransporteRepository extends JpaRepository<DetalleTransporte, Integer>, JpaSpecificationExecutor<DetalleTransporte> {
}