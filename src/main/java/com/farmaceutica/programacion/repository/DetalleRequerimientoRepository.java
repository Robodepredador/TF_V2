package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.programacion.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleRequerimientoRepository extends JpaRepository<DetalleRequerimiento, Integer> {

    // El método que tu service necesita para buscar los detalles
    // Asegúrate de que el nombre del campo en DetalleRequerimiento sea 'requerimiento'
    List<DetalleRequerimiento> findByRequerimiento(Requerimiento requerimiento);
}