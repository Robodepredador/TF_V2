package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>, JpaSpecificationExecutor<Inventario> {
    List<Inventario> findByIdLote_IdProducto_Id(Integer idProducto);
}