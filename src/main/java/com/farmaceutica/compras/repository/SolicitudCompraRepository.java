package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.SolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudCompraRepository extends JpaRepository<SolicitudCompra, Integer> {
}