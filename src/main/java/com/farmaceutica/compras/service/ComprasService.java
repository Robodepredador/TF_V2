package com.farmaceutica.compras.service;

// Importas las entidades de TU módulo que este contrato necesita
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.model.DetalleRequerimiento;

import org.springframework.stereotype.Service;
import java.util.List;

@Service // <-- ¡Esto es lo que soluciona el error "Could not autowire"!
public class ComprasService {

    // (Constructor vacío por ahora, luego el dev de 'compras' lo llenará)
    public ComprasService() { }

    /**
     * === CONTRATO PARA 'programacion' ===
     * Este método será llamado por ProgramacionService
     * para crear una nueva Solicitud de Compra.
     */
    public void crearSolicitudDesdeRequerimiento(Requerimiento req, List<DetalleRequerimiento> detallesParaComprar) {

        // TODO: Lógica del módulo de Compras
        // Esta parte la implementará el otro desarrollador.
        System.out.println("COMPRAS_SERVICE: [TODO] Lógica aún no implementada.");
    }
}