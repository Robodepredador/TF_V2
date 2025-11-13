package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.SolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SolicitudCompraRepository extends JpaRepository<SolicitudCompra, Integer>, JpaSpecificationExecutor<SolicitudCompra> {
}