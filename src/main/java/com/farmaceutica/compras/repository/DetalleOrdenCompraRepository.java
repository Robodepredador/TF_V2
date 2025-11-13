package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer> , JpaSpecificationExecutor<DetalleOrdenCompra> {
  }