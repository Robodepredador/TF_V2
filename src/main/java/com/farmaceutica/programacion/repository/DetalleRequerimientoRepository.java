package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.DetalleRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface DetalleRequerimientoRepository extends JpaRepository<DetalleRequerimiento, Integer> , JpaSpecificationExecutor<DetalleRequerimiento> {
    List<DetalleRequerimiento> findByIdRequerimiento_Id(Integer idRequerimiento);
}
