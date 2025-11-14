package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.DetalleOrdenCompra;
import com.farmaceutica.compras.model.OrdenCompra;
import com.farmaceutica.compras.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer>, JpaSpecificationExecutor<DetalleOrdenCompra> {

  // Listar detalles de una orden
  List<DetalleOrdenCompra> findByIdOrden(OrdenCompra orden);

  // Detalle específico de producto dentro de una orden
  Optional<DetalleOrdenCompra> findByIdOrdenAndIdProducto(OrdenCompra orden, Producto producto);

  // Ver si un producto ya existe en una orden
  boolean existsByIdOrdenAndIdProducto(OrdenCompra orden, Producto producto);
}
