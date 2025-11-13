package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetalleSolicitudCompraRepository extends JpaRepository<DetalleSolicitudCompra, Integer>, JpaSpecificationExecutor<DetalleSolicitudCompra> {
}