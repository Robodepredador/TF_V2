package com.farmaceutica.almacenamiento.service;

import com.farmaceutica.almacenamiento.model.*;
import com.farmaceutica.almacenamiento.repository.*;
import com.farmaceutica.compras.model.DetalleOrdenCompra;
import com.farmaceutica.compras.model.OrdenCompra;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceActualizarInventario {

    private final InventarioRepository inventarioRepository;
    private final MovimientoInventarioRepository movimientoRepository;
    private final AlmacenRepository almacenRepository;
    private final LoteProductoRepository loteRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleOrdenCompraRepository detalleOrdenRepository;

    private final AlmacenamientoMapper mapper;

    // =============================================================
    //                 CONSULTAS DE INVENTARIO
    // =============================================================
    public InventarioResponseDTO consultarInventario(Integer idInventario) {
        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        return mapper.toInventarioResponseDTO(inventario);
    }

    public List<InventarioResponseDTO> consultarInventarioPorAlmacen(Integer idAlmacen) {
        Almacen almacen = almacenRepository.findById(idAlmacen)
                .orElseThrow(() -> new EntityNotFoundException("Almacén no encontrado"));

        return inventarioRepository.findByIdAlmacen(almacen)
                .stream()
                .map(mapper::toInventarioResponseDTO)
                .toList();
    }

    public InventarioResponseDTO consultarInventarioPorLote(Integer idLote) {
        LoteProducto lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));

        Inventario inventario = inventarioRepository.findByIdLote(lote)
                .orElseThrow(() -> new EntityNotFoundException("Inventario del lote no encontrado"));

        return mapper.toInventarioResponseDTO(inventario);
    }

    // =============================================================
    //        CONSULTAR ORDEN DE COMPRA DESDE INVENTARIO/LOTE
    // =============================================================
    public OrdenCompra consultarOrdenCompraDeLote(Integer idLote) {
        LoteProducto lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));

        if (lote.getIdOrdenCompra() == null)
            throw new EntityNotFoundException("El lote no pertenece a ninguna orden de compra");

        return lote.getIdOrdenCompra();
    }

    public List<DetalleOrdenCompra> consultarDetalleDeOrden(Integer idOrden) {
        OrdenCompra orden = ordenCompraRepository.findById(idOrden)
                .orElseThrow(() -> new EntityNotFoundException("Orden de Compra no encontrada"));

        return detalleOrdenRepository.findByIdOrden(orden);
    }

    // =============================================================
    //                 ACTUALIZAR INVENTARIO
    // =============================================================
    @Transactional
    public InventarioResponseDTO actualizarInventario(InventarioUpdateRequestDTO dto) {

        Inventario inventario = inventarioRepository.findById(dto.idInventario())
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        Integer stockAnterior = inventario.getStockActual();
        Integer stockNuevo = dto.stockActual();

        // Actualizar campos básicos (MapStruct)
        mapper.updateInventarioFromDTO(dto, inventario);

        inventarioRepository.save(inventario);

        // Registrar movimiento por diferencia de stock
        if (stockNuevo != null && !stockNuevo.equals(stockAnterior)) {
            registrarMovimientoPorActualizacion(inventario, stockAnterior, stockNuevo);
        }

        return mapper.toInventarioResponseDTO(inventario);
    }

    // =============================================================
    //        REGISTRO AUTOMÁTICO DE MOVIMIENTO DE INVENTARIO
    // =============================================================
    private void registrarMovimientoPorActualizacion(Inventario inventario, Integer stockAnterior, Integer stockNuevo) {

        MovimientoInventario movimiento = new MovimientoInventario();

        // Determinar tipo de movimiento
        if (stockNuevo > stockAnterior) {
            movimiento.setTipoMovimiento("AJUSTE_POSITIVO");
            movimiento.setCantidad(stockNuevo - stockAnterior);
        } else {
            movimiento.setTipoMovimiento("AJUSTE_NEGATIVO");
            movimiento.setCantidad(stockAnterior - stockNuevo);
        }

        movimiento.setIdInventario(inventario);
        movimiento.setTipoReferencia("INVENTARIO");
        movimiento.setIdReferencia(inventario.getId());
        movimiento.setObservacion("Actualización manual de inventario");

        movimientoRepository.save(movimiento);
    }

}
