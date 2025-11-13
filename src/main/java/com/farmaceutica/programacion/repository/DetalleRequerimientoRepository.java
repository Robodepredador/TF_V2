package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.DetalleRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetalleRequerimientoRepository extends JpaRepository<DetalleRequerimiento, Integer> , JpaSpecificationExecutor<DetalleRequerimiento> {
  }