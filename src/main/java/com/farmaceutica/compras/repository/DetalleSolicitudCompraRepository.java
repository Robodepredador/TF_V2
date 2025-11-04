package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.DetalleSolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSolicitudCompraRepository extends JpaRepository<DetalleSolicitudCompra, Integer> {
}