package com.farmaceutica.almacenamiento.repository;

import com.farmaceutica.almacenamiento.model.LoteProducto;
import com.farmaceutica.compras.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoteProductoRepository extends JpaRepository<LoteProducto, Integer>, JpaSpecificationExecutor<LoteProducto> {

    List<LoteProducto> findByIdProducto(Producto producto);

    Optional<LoteProducto> findByNumeroLote(String numeroLote);

    List<LoteProducto> findByEstado(String estado);

    List<LoteProducto> findByIdOrdenCompra_Id(Integer idOrden);

    List<LoteProducto> findByFechaVencimientoBefore(LocalDate fecha);

}
