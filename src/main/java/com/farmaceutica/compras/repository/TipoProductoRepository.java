package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Integer>, JpaSpecificationExecutor<TipoProducto> {

  Optional<TipoProducto> findByNombreTipo(String nombreTipo);

  boolean existsByNombreTipo(String nombreTipo);
}
