package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.SolicitudCompra;
import com.farmaceutica.programacion.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudCompraRepository extends JpaRepository<SolicitudCompra, Integer>, JpaSpecificationExecutor<SolicitudCompra> {

    // Solicitudes por requerimiento
    List<SolicitudCompra> findByIdRequerimiento(Requerimiento requerimiento);

    // Filtrar por estado
    List<SolicitudCompra> findByEstado(String estado);
}
