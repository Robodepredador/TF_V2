package com.farmaceutica.programacion.repository;

import com.farmaceutica.programacion.model.SolicitudCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SolicitudCompraRepository extends JpaRepository<SolicitudCompra, Integer> {
    /**
     * Busca todas las solicitudes de compra que coincidan con un estado.
     * (Ej. "Pendiente").
     */
    List<SolicitudCompra> findByEstado(String estado);

    /**
     * (Opcional, pero recomendado)
     * Busca solicitudes por una lista de estados.
     * (Ej. "Pendiente", "Aprobada").
     */
    List<SolicitudCompra> findByEstadoIn(Collection<String> estados);
}