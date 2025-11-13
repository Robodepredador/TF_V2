package com.farmaceutica.programacion.service;

import com.farmaceutica.programacion.dto.*;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.repository.DetalleRequerimientoRepository;
import com.farmaceutica.programacion.repository.RequerimientoRepository;
import com.farmaceutica.almacenamiento.model.Inventario;
import com.farmaceutica.almacenamiento.model.LoteProducto;
import com.farmaceutica.almacenamiento.repository.InventarioRepository;
import com.farmaceutica.almacenamiento.repository.LoteProductoRepository;
import com.farmaceutica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que gestiona las operaciones del módulo de Programación:
 * - Consulta de requerimientos pendientes
 * - Consulta de detalles de requerimientos
 * - Consulta de inventario y lotes de productos
 */
@Service
@RequiredArgsConstructor
public class ServiceRegistrarProgramacionRequerimiento {

    private final RequerimientoRepository requerimientoRepository;
    private final DetalleRequerimientoRepository detalleRequerimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteProductoRepository loteProductoRepository;
    private final ProgramacionMapper mapper;

    /**
     * Lista los requerimientos en estado "Pendiente".
     */
    @Transactional(readOnly = true)
    public List<RequerimientoDTO> listarRequerimientosPendientes() {
        List<Requerimiento> requerimientos =
                requerimientoRepository.findByEstadoOrderByFechaCreacionDesc("Pendiente");
        return mapper.toRequerimientoDTOList(requerimientos);
    }

    /**
     * Obtiene el detalle (productos solicitados) de un requerimiento específico.
     */
    @Transactional(readOnly = true)
    public List<DetalleRequerimientoDTO> obtenerDetalleRequerimiento(Integer idRequerimiento) {
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento no encontrado: " + idRequerimiento));

        List<DetalleRequerimiento> detalles =
                detalleRequerimientoRepository.findByIdRequerimiento_Id(idRequerimiento);

        return mapper.toDetalleRequerimientoDTOList(detalles);
    }

    /**
     * Consulta el inventario total de un producto (sumando todos sus registros).
     */
    @Transactional(readOnly = true)
    public InventarioDTO consultarInventarioPorProducto(Integer idProducto) {
        List<Inventario> inventarios = inventarioRepository.findByIdLote_IdProducto_Id(idProducto);

        if (inventarios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró inventario para el producto: " + idProducto);
        }

        // Mapeamos el primer inventario y sumamos el total de stock
        InventarioDTO dto = mapper.toInventarioDTO(inventarios.get(0));
        int stockTotal = inventarios.stream()
                .mapToInt(inv -> inv.getStockActual() != null ? inv.getStockActual() : 0)
                .sum();
        dto.setStockTotal(stockTotal);

        return dto;
    }

    /**
     * Lista los lotes asociados a un producto.
     */
    @Transactional(readOnly = true)
    public List<LoteDTO> consultarLotesDeProducto(Integer idProducto) {
        List<LoteProducto> lotes = loteProductoRepository.findByIdProducto_Id(idProducto);

        if (lotes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron lotes para el producto: " + idProducto);
        }

        return mapper.toLoteDTOList(lotes);
    }

    /**
     * Consulta la información detallada de un lote.
     */
    @Transactional(readOnly = true)
    public LoteDTO consultarDetalleLote(Integer idLote) {
        LoteProducto lote = loteProductoRepository.findById(idLote)
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado: " + idLote));

        LoteDTO dto = mapper.toLoteDTO(lote);
        return dto;
    }
}
