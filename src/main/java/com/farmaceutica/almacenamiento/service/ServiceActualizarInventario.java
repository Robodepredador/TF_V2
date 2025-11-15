
package com.farmaceutica.almacenamiento.service;

import com.farmaceutica.almacenamiento.dto.AjusteInventarioDto;
import com.farmaceutica.almacenamiento.dto.InventarioDto;
import com.farmaceutica.almacenamiento.dto.LoteProductoDto;

import java.util.List;

public interface ServiceActualizarInventario {

    /**
     * Realiza un ajuste de inventario (positivo o negativo).
     * Simplemente registra un MovimientoInventario.
     * Los Triggers de BD harán la validación de stock y la actualización.
     *
     * @param dto DTO con los detalles del ajuste.
     */
    void actualizarInventario(AjusteInventarioDto dto);

    /**
     * Consulta el inventario (stock actual por lote y almacén).
     */
    List<InventarioDto> consultarInventario();

    /**
     * Consulta el detalle de un item de inventario.
     */
    InventarioDto consultarDetalleInventario(Integer idInventario);

    /**
     * Consulta el detalle de un lote.
     */
    LoteProductoDto consultarDetalleLote(Integer idLote);
}