package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.FormaFarmaceutica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FormaFarmaceuticaRepository extends JpaRepository<FormaFarmaceutica, Integer> , JpaSpecificationExecutor<FormaFarmaceutica> {
  }