package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.ProductoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoProveedorRepository extends JpaRepository<ProductoProveedor, Integer> {
    /**
     * Busca una cotización (un precio) específica usando la combinación
     * de Producto y Proveedor.
     */
    Optional<ProductoProveedor> findByIdProducto_IdAndIdProveedor_Id(Integer idProducto, Integer idProveedor);

    /**
     * (Este ya lo creamos para el servicio de Programación,
     * pero asegúrate de que esté)
     * Busca la primera cotización que encuentre para un producto.
     */
    Optional<ProductoProveedor> findFirstByIdProducto_Id(Integer idProducto);

    /**
     * Busca TODAS las cotizaciones (proveedores) de un producto específico.
     */
    List<ProductoProveedor> findByIdProducto_Id(Integer idProducto);
}