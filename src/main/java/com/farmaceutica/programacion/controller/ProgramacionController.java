package com.farmaceutica.programacion.controller;

import com.farmaceutica.programacion.dto.*;
import com.farmaceutica.programacion.service.ServiceRegistrarProgramacionRequerimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del módulo de Programación de Requerimientos.
 * Expone endpoints para que el programador consulte requerimientos,
 * detalles, inventarios y lotes.
 */
@RestController
@RequestMapping("/api/programacion")
@RequiredArgsConstructor
public class ProgramacionController {

    private final ServiceRegistrarProgramacionRequerimiento service;

    /**
     * Endpoint para listar requerimientos pendientes.
     * GET /api/programacion/requerimientos
     */
    @GetMapping("/requerimientos")
    public ResponseEntity<List<RequerimientoDTO>> listarRequerimientosPendientes() {
        List<RequerimientoDTO> lista = service.listarRequerimientosPendientes();
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint para consultar los detalles de un requerimiento.
     * GET /api/programacion/requerimientos/{id}/detalles
     */
    @GetMapping("/requerimientos/{id}/detalles")
    public ResponseEntity<List<DetalleRequerimientoDTO>> obtenerDetalleRequerimiento(@PathVariable Integer id) {
        List<DetalleRequerimientoDTO> detalles = service.obtenerDetalleRequerimiento(id);
        return ResponseEntity.ok(detalles);
    }

    /**
     * Endpoint para consultar el inventario de un producto.
     * GET /api/programacion/inventario/{idProducto}
     */
    @GetMapping("/inventario/{idProducto}")
    public ResponseEntity<InventarioDTO> consultarInventarioPorProducto(@PathVariable Integer idProducto) {
        InventarioDTO inventario = service.consultarInventarioPorProducto(idProducto);
        return ResponseEntity.ok(inventario);
    }

    /**
     * Endpoint para consultar los lotes de un producto.
     * GET /api/programacion/lotes/{idProducto}
     */
    @GetMapping("/lotes/{idProducto}")
    public ResponseEntity<List<LoteDTO>> consultarLotesDeProducto(@PathVariable Integer idProducto) {
        List<LoteDTO> lotes = service.consultarLotesDeProducto(idProducto);
        return ResponseEntity.ok(lotes);
    }

    /**
     * Endpoint para consultar el detalle de un lote específico.
     * GET /api/programacion/lote/{idLote}
     */
    @GetMapping("/lote/{idLote}")
    public ResponseEntity<LoteDTO> consultarDetalleLote(@PathVariable Integer idLote) {
        LoteDTO lote = service.consultarDetalleLote(idLote);
        return ResponseEntity.ok(lote);
    }
}
