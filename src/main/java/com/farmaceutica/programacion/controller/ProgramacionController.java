package com.farmaceutica.programacion.controller;

import com.farmaceutica.almacenamiento.dto.InventarioStockDto;
import com.farmaceutica.exception.BusinessException;
import com.farmaceutica.programacion.dto.DetalleRequerimientoDto;
import com.farmaceutica.programacion.dto.ProgramacionResultadoDto;
import com.farmaceutica.programacion.dto.RequerimientoResumenDto;
import com.farmaceutica.programacion.service.ServiceRegistrarOrden;
import com.farmaceutica.programacion.service.ServiceRegistrarProgramacionRequerimiento;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programacion")
@RequiredArgsConstructor
public class ProgramacionController {

    private final ServiceRegistrarProgramacionRequerimiento serviceProgramacion;
    private final ServiceRegistrarOrden serviceRegistrarOrden;

    // ===============================
    // HANDLER DE ERRORES
    // ===============================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> manejarBusiness(BusinessException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> manejarNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of(
                "success", false,
                "message", ex.getMessage()
        ));
    }

    // ===========================================================
    // GET — Requerimientos pendientes
    // ===========================================================
    @GetMapping("/requerimientos/pendientes")
    public ResponseEntity<?> listarRequerimientosPendientes() {

        List<RequerimientoResumenDto> data =
                serviceProgramacion.consultarRequerimientosPendientes();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Requerimientos pendientes obtenidos correctamente",
                "data", data
        ));
    }

    // ===========================================================
    // GET — Detalles de un requerimiento
    // ===========================================================
    @GetMapping("/requerimientos/{id}/detalles")
    public ResponseEntity<?> listarDetalles(@PathVariable Integer id) {

        List<DetalleRequerimientoDto> data =
                serviceProgramacion.consultarDetallesRequerimiento(id);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Detalles del requerimiento obtenidos correctamente",
                "data", data
        ));
    }

    // ===========================================================
    // GET — Stock de un producto
    // ===========================================================
    @GetMapping("/productos/{idProducto}/stock")
    public ResponseEntity<?> obtenerStock(@PathVariable Integer idProducto) {

        List<InventarioStockDto> data =
                serviceProgramacion.consultarStockDeProducto(idProducto);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Stock del producto obtenido correctamente",
                "data", data
        ));
    }

    // ===========================================================
    // POST — Procesamiento automático (OPCIÓN A)
    // ===========================================================
    @PostMapping("/aceptar/{idRequerimiento}")
    public ResponseEntity<?> aceptarRequerimiento(@PathVariable Integer idRequerimiento) {

        ProgramacionResultadoDto result = serviceRegistrarOrden.procesarAutomatico(idRequerimiento);

        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("message", "Requerimiento atendido automáticamente.");
        body.put("idOrdenDistribucion", result.idOrdenDistribucion());
        body.put("idSolicitudCompra", result.idSolicitudCompra());

        return ResponseEntity.ok(body);

    }
}
