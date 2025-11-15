package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteProductoRepository extends JpaRepository<LoteProducto, Integer> {
}