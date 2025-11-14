package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.Almacen;
import com.farmaceutica.almacenamiento.model.Inventario;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Integer>, JpaSpecificationExecutor<Inventario> {

    Optional<Inventario> findByIdLote(LoteProducto lote);

    Optional<Inventario> findByIdAlmacenAndIdLote(Almacen almacen, LoteProducto lote);

    List<Inventario> findByIdAlmacen(Almacen almacen);

    List<Inventario> findByStockActualLessThan(Integer stock);

}
