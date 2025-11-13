package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> , JpaSpecificationExecutor<ProductoProveedor> {
  }