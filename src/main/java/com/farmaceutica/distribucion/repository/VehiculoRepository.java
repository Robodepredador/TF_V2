package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository
        extends JpaRepository<Vehiculo, Integer>, JpaSpecificationExecutor<Vehiculo> {

    Optional<Vehiculo> findByPlaca(String placa);

    List<Vehiculo> findByEstado(String estado);

    List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);

}
