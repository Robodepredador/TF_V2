package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.MovimientoInventario;
import com.farmaceutica.almacenamiento.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer>, JpaSpecificationExecutor<MovimientoInventario> {

    List<MovimientoInventario> findByIdInventario(Inventario inventario);

    List<MovimientoInventario> findByTipoMovimiento(String tipoMovimiento);

    List<MovimientoInventario> findByFechaMovimientoBetween(Instant inicio, Instant fin);

}
