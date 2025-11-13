package com.farmaceutica.core.repository;

import com.farmaceutica.core.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // <-- Esto lo registra como un Bean y soluciona el 'autowire'
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

}