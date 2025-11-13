package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> , JpaSpecificationExecutor<Proveedor> {
  }