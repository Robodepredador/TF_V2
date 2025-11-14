package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.IncidenciaLote;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IncidenciaLoteRepository extends JpaRepository<IncidenciaLote, Integer>, JpaSpecificationExecutor<IncidenciaLote> {

    List<IncidenciaLote> findByIdLote(LoteProducto lote);

    List<IncidenciaLote> findByEstado(String estado);

}
