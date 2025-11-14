package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.model.TipoProducto;
import com.farmaceutica.compras.model.FormaFarmaceutica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>, JpaSpecificationExecutor<Producto> {

  // Validaciones
  Optional<Producto> findByNombreProducto(String nombreProducto);
  Optional<Producto> findByCodigoDigemid(String codigoDigemid);
  Optional<Producto> findByRegistroSanitario(String registroSanitario);

  boolean existsByNombreProducto(String nombreProducto);
  boolean existsByCodigoDigemid(String codigoDigemid);
  boolean existsByRegistroSanitario(String registroSanitario);

  // Listar activos
  List<Producto> findByEstadoTrue();

  // Filtrado por búsqueda parcial
  List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);

  // Filtrar por tipo o forma
  List<Producto> findByIdTipo(TipoProducto tipo);
  List<Producto> findByIdForma(FormaFarmaceutica forma);
}
