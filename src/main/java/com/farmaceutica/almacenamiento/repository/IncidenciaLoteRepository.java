package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.IncidenciaLote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncidenciaLoteRepository extends JpaRepository<IncidenciaLote, Integer>, JpaSpecificationExecutor<IncidenciaLote> {
}