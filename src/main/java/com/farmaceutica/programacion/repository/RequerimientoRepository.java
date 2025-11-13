package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> , JpaSpecificationExecutor<Requerimiento> {

    List<Requerimiento> findByEstadoOrderByFechaCreacionDesc(String estado);
}