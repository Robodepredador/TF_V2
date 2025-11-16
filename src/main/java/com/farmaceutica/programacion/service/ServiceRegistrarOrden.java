package com.farmaceutica.programacion.service;

import com.farmaceutica.programacion.dto.ProgramacionRequestDto;

public interface ServiceRegistrarOrden {

    /**
     * Método principal (EL FORK).
     * Procesa un requerimiento pendiente y crea una Orden de Compra (Solicitud)
     * o una Orden de Distribución, según la lógica de stock y la decisión
     * del supervisor.
     *
     * @param request DTO que contiene el ID del requerimiento y la decisión.
     */
    void registrarOrdenEspecifica(ProgramacionRequestDto request);
}