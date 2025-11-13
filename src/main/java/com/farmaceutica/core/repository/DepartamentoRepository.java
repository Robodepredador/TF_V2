package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> , JpaSpecificationExecutor<Departamento> {
  }