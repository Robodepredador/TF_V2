package com.farmaceutica.programacion.controller;

import com.farmaceutica.programacion.dto.CrearRequerimientoRequest;
import com.farmaceutica.programacion.dto.RequerimientoDto; // <-- DTO de respuesta
import com.farmaceutica.programacion.service.ProgramacionService;
import jakarta.validation.Valid; // <-- Importa Valid
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programacion")
public class ProgramacionController {

    private final ProgramacionService programacionService;

    public ProgramacionController(ProgramacionService programacionService) {
        this.programacionService = programacionService;
    }

    // Endpoint para el Caso de Uso 1: Crear un Requerimiento
    @PostMapping("/requerimientos")
    public ResponseEntity<RequerimientoDto> crearNuevoRequerimiento(
            @Valid @RequestBody CrearRequerimientoRequest request) { // <-- @Valid activa las validaciones del DTO

        // ¡ESTO AHORA FUNCIONARÁ!
        RequerimientoDto dtoRespuesta = programacionService.crearRequerimiento(request);

        return new ResponseEntity<>(dtoRespuesta, HttpStatus.CREATED);
    }

    // Endpoint para el Caso de Uso 2: Procesar un Requerimiento
    @PostMapping("/requerimientos/{id}/procesar")
    public ResponseEntity<Void> procesarRequerimiento(@PathVariable Integer id) {
        programacionService.programarRequerimiento(id);
        return ResponseEntity.ok().build();
    }

    // (Aquí irían tus GETs para listar requerimientos, etc.)
}