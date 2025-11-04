package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // <-- Registra esto como un Bean
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {
    // Spring Data JPA te da 'save()', 'findById()', etc. ¡GRATIS!
}