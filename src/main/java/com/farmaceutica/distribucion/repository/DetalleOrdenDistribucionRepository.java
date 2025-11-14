package com.farmaceutica.distribucion.repository;

import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.model.Vehiculo;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import com.farmaceutica.compras.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DetalleOrdenDistribucionRepository
        extends JpaRepository<DetalleOrdenDistribucion, Integer>, JpaSpecificationExecutor<DetalleOrdenDistribucion> {

    List<DetalleOrdenDistribucion> findByIdOrdenDist(OrdenDistribucion orden);

    List<DetalleOrdenDistribucion> findByIdVehiculoAsignado(Vehiculo vehiculo);

    List<DetalleOrdenDistribucion> findByIdLote(LoteProducto lote);

    List<DetalleOrdenDistribucion> findByIdProducto(Producto producto);

}
