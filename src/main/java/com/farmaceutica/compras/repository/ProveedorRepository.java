package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>, JpaSpecificationExecutor<Proveedor> {

  // Buscar proveedor por RUC (validación de duplicados)
  Optional<Proveedor> findByRuc(String ruc);

  // Buscar proveedor por nombre exacto
  Optional<Proveedor> findByNombreProveedor(String nombreProveedor);

  // Validar si existe proveedor con RUC
  boolean existsByRuc(String ruc);

  // Validar si existe con nombre
  boolean existsByNombreProveedor(String nombreProveedor);

  // Listar proveedores activos
  List<Proveedor> findByEstadoTrue();

  // Búsqueda por coincidencia parcial de nombre
  List<Proveedor> findByNombreProveedorContainingIgnoreCase(String nombreProveedor);

}
