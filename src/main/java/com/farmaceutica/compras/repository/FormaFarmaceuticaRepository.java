package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.FormaFarmaceutica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaFarmaceuticaRepository extends JpaRepository<FormaFarmaceutica, Integer>, JpaSpecificationExecutor<FormaFarmaceutica> {

  Optional<FormaFarmaceutica> findByNombreForma(String nombreForma);

  boolean existsByNombreForma(String nombreForma);
}
