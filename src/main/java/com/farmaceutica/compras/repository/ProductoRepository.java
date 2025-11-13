package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoRepository extends JpaRepository<Producto, Integer> , JpaSpecificationExecutor<Producto> {
  }