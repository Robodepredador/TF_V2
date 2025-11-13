package com.farmaceutica.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // <-- Esto lo registra como un Bean de Spring
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}