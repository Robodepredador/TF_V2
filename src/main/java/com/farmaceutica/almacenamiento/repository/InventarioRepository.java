package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>, JpaSpecificationExecutor<Inventario> {
}