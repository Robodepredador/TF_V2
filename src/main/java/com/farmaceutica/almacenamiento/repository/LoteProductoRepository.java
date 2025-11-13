package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoteProductoRepository extends JpaRepository<LoteProducto, Integer>, JpaSpecificationExecutor<LoteProducto> {
}