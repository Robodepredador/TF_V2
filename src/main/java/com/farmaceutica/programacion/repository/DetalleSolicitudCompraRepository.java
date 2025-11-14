package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.DetalleSolicitudCompra;
import com.farmaceutica.programacion.model.SolicitudCompra;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.compras.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleSolicitudCompraRepository extends JpaRepository<DetalleSolicitudCompra, Integer>, JpaSpecificationExecutor<DetalleSolicitudCompra> {

    // Detalles por solicitud
    List<DetalleSolicitudCompra> findByIdSolicitud(SolicitudCompra solicitud);

    // Detalles por proveedor sugerido
    List<DetalleSolicitudCompra> findByIdProveedorSeleccionado(Proveedor proveedor);

    // Filtrar detalles por producto
    List<DetalleSolicitudCompra> findByIdProducto(Producto producto);
}
