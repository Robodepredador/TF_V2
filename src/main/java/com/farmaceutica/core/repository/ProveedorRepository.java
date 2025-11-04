package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // <-- Esto lo registra como un Bean y soluciona el 'autowire'
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    // Spring te da 'findById()', 'save()', etc. gratis.

    // Esto es para la lógica que implementamos en ComprasService (aunque ahora
    // esté en ProgramacionService, es útil tenerla)
    // Busca un proveedor basado en el ID del producto que vende.
    // (Requiere que la entidad ProductoProveedor esté mapeada)
    // Proveedor findFirstByProductoProveedors_Producto_Id(Integer idProducto);
}