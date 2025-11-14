package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.model.ProductoProveedor;
import com.farmaceutica.compras.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer>, JpaSpecificationExecutor<ProductoProveedor> {

  // Obtener relación por producto y proveedor
  Optional<ProductoProveedor> findByIdProductoAndIdProveedor(Producto producto, Proveedor proveedor);

  // Listar proveedores de un producto
  List<ProductoProveedor> findByIdProducto(Producto producto);

  // Listar productos que maneja un proveedor
  List<ProductoProveedor> findByIdProveedor(Proveedor proveedor);

  // Validación para evitar registros duplicados
  boolean existsByIdProductoAndIdProveedor(Producto producto, Proveedor proveedor);

  // Solo registros activos
  List<ProductoProveedor> findByEstadoTrue();
}
