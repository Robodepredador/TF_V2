package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AlmacenRepository extends JpaRepository<Almacen, Integer>, JpaSpecificationExecutor<Almacen> {

    List<Almacen> findByEstadoTrue();

    Optional<Almacen> findByNombreAlmacen(String nombreAlmacen);

    List<Almacen> findByTipoAlmacen(String tipoAlmacen);

}
