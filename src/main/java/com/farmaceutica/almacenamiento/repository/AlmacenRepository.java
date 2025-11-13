package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer>, JpaSpecificationExecutor<Almacen> {
}