package com.farmaceutica.compras.repository;

import com.farmaceutica.compras.model.OrdenCompra;
import com.farmaceutica.programacion.model.SolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer>, JpaSpecificationExecutor<OrdenCompra> {

  // Buscar orden por su número único
  Optional<OrdenCompra> findByNumeroOrden(String numeroOrden);

  boolean existsByNumeroOrden(String numeroOrden);

  // Listar órdenes por solicitud
  List<OrdenCompra> findByIdSolicitud(SolicitudCompra solicitud);

  // Filtro por estado ("Pendiente", "Aprobada", etc)
  List<OrdenCompra> findByEstado(String estado);

  // Buscar por proveedor si lo usas (aunque tu entidad lo guarda como Integer)
  List<OrdenCompra> findByIdProveedor(Integer idProveedor);
}
