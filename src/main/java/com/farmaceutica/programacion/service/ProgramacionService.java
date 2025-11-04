package com.farmaceutica.programacion.service;

// --- Imports de CORE (Fundación) ---
import com.farmaceutica.core.model.Departamento;
import com.farmaceutica.core.model.Producto;
import com.farmaceutica.core.model.Proveedor; // <-- NUEVO: Para asignar proveedor
import com.farmaceutica.core.model.VwStockActual;
import com.farmaceutica.core.repository.DepartamentoRepository;
import com.farmaceutica.core.repository.ProductoRepository;
import com.farmaceutica.core.repository.ProveedorRepository; // <-- NUEVO
import com.farmaceutica.core.repository.VwStockActualRepository;

// --- Imports de TU MÓDULO (programacion) ---
import com.farmaceutica.programacion.dto.CrearRequerimientoRequest;
import com.farmaceutica.programacion.dto.RequerimientoDto;
import com.farmaceutica.programacion.mapper.RequerimientoMapper;
import com.farmaceutica.programacion.model.DetalleRequerimiento;
import com.farmaceutica.programacion.model.Requerimiento;
import com.farmaceutica.programacion.repository.DetalleRequerimientoRepository;
import com.farmaceutica.programacion.repository.RequerimientoRepository;

// --- Imports de OTROS MÓDULOS (¡Tú los creas!) ---
import com.farmaceutica.compras.model.DetalleSolicitudCompra;
import com.farmaceutica.compras.model.SolicitudCompra;
import com.farmaceutica.compras.repository.DetalleSolicitudCompraRepository;
import com.farmaceutica.compras.repository.SolicitudCompraRepository;
import com.farmaceutica.distribucion.model.DetalleOrdenDistribucion;
import com.farmaceutica.distribucion.model.OrdenDistribucion;
import com.farmaceutica.distribucion.repository.DetalleOrdenDistribucionRepository;
import com.farmaceutica.distribucion.repository.OrdenDistribucionRepository;

// --- Imports de Java y Spring ---
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramacionService {

    // --- Repositorios de TU Módulo ---
    private final RequerimientoRepository requerimientoRepository;
    private final DetalleRequerimientoRepository detalleRequerimientoRepository;

    // --- Repositorios de CORE ---
    private final VwStockActualRepository stockRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository; // <-- NUEVA DEPENDENCIA

    // --- Repositorios de OTROS MÓDULOS (que tú gestionas) ---
    private final SolicitudCompraRepository solicitudCompraRepository;
    private final DetalleSolicitudCompraRepository detalleSolicitudCompraRepository;
    private final OrdenDistribucionRepository ordenDistribucionRepository;
    private final DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository;

    // CONSTRUCTOR REFACTORIZADO: Inyecta todos los repositorios
    public ProgramacionService(RequerimientoRepository requerimientoRepository,
                               DetalleRequerimientoRepository detalleRequerimientoRepository,
                               VwStockActualRepository stockRepository,
                               DepartamentoRepository departamentoRepository,
                               ProductoRepository productoRepository,
                               ProveedorRepository proveedorRepository, // <-- AÑADIDO
                               SolicitudCompraRepository solicitudCompraRepository, // <-- AÑADIDO
                               DetalleSolicitudCompraRepository detalleSolicitudCompraRepository, // <-- AÑADIDO
                               OrdenDistribucionRepository ordenDistribucionRepository, // <-- AÑADIDO
                               DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository // <-- AÑADIDO
            /* Ya NO inyectamos ComprasService ni DistribucionService */
    ) {
        this.requerimientoRepository = requerimientoRepository;
        this.detalleRequerimientoRepository = detalleRequerimientoRepository;
        this.stockRepository = stockRepository;
        this.departamentoRepository = departamentoRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.solicitudCompraRepository = solicitudCompraRepository;
        this.detalleSolicitudCompraRepository = detalleSolicitudCompraRepository;
        this.ordenDistribucionRepository = ordenDistribucionRepository;
        this.detalleOrdenDistribucionRepository = detalleOrdenDistribucionRepository;
    }

    // --- CASO DE USO 1: "Crear Requerimiento" (Esta lógica no cambia) ---
    @Transactional
    public RequerimientoDto crearRequerimiento(CrearRequerimientoRequest request) {
        // ... (El código que ya teníamos para crear el requerimiento está perfecto)
        // ... (Este método funciona tal cual)
        System.out.println("\n--- INICIANDO: crearRequerimiento ---");

        Departamento depto = departamentoRepository.findById(request.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado: " + request.getIdDepartamento()));

        Requerimiento nuevoReq = RequerimientoMapper.toEntity(request, depto);
        Requerimiento reqGuardado = requerimientoRepository.save(nuevoReq);

        List<DetalleRequerimiento> detalles = request.getItems().stream()
                .map(itemDto -> {
                    Producto prod = productoRepository.findById(itemDto.getIdProducto())
                            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + itemDto.getIdProducto()));

                    return RequerimientoMapper.itemToDetalle(itemDto, reqGuardado, prod);
                }).collect(Collectors.toList());

        detalleRequerimientoRepository.saveAll(detalles);

        System.out.println("--- FINALIZADO: crearRequerimiento (ID: " + reqGuardado.getId() + ") ---");
        return RequerimientoMapper.toDto(reqGuardado, detalles.size());
    }


    // --- CASO DE USO 2: "Programar Requerimiento" (¡LÓGICA REFACTORIZADA!) ---
    @Transactional
    public void programarRequerimiento(Integer idRequerimiento) {

        System.out.println("\n\n--- ¡INICIANDO PROGRAMACION DE REQUERIMIENTO " + idRequerimiento + "! ---\n");

        Requerimiento req = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new EntityNotFoundException("Requerimiento no encontrado: " + idRequerimiento));

        // (Validación anti-doble-procesamiento)
        if (!"Pendiente".equals(req.getEstado())) {
            System.out.println("ADVERTENCIA: El Requerimiento " + idRequerimiento + " ya fue procesado. Estado actual: " + req.getEstado());
            return;
        }

        List<DetalleRequerimiento> detalles = detalleRequerimientoRepository.findByRequerimiento(req);
        if (detalles.isEmpty()) { return; } // No hay nada que hacer

        List<DetalleRequerimiento> itemsParaComprar = new ArrayList<>();
        List<DetalleRequerimiento> itemsParaDistribuir = new ArrayList<>();

        // --- LÓGICA DE DECISIÓN (Esto no cambia) ---
        for (DetalleRequerimiento detalle : detalles) {
            List<VwStockActual> lotes = stockRepository.findByIdProductoOrderByFechaVencimientoAsc(detalle.getProducto().getId());
            int stockTotal = lotes.stream().mapToInt(VwStockActual::getStockActual).sum();

            if (stockTotal >= detalle.getCantidad()) {
                System.out.println("...DECISIÓN: Producto ID " + detalle.getProducto().getId() + " -> A DISTRIBUCION");
                itemsParaDistribuir.add(detalle);
            } else {
                System.out.println("...DECISIÓN: Producto ID " + detalle.getProducto().getId() + " -> A COMPRAS");
                // TODO: Implementar lógica de llenado parcial si se desea
                itemsParaComprar.add(detalle);
            }
        }

        // --- HANDOFF (¡Refactorizado! Ahora creamos los documentos nosotros) ---
        if (!itemsParaComprar.isEmpty()) {
            System.out.println("...EJECUTANDO: Creando Solicitud de Compra...");
            crearSolicitudDeCompra(req, itemsParaComprar);
        }

        if (!itemsParaDistribuir.isEmpty()) {
            System.out.println("...EJECUTANDO: Creando Orden de Distribución (con lógica FEFO)...");
            crearOrdenDeDistribucion(req, itemsParaDistribuir);
        }

        // --- 4. Actualizar estado ---
        System.out.println("...ACTUALIZANDO estado del requerimiento a 'En Proceso'.");
        req.setEstado("En Proceso");
        requerimientoRepository.save(req);

        System.out.println("\n--- ¡PROGRAMACION FINALIZADA! (Haciendo COMMIT...) ---\n\n");
    }


    // ===================================================================
    // MÉTODOS PRIVADOS (Aquí vive la lógica de creación)
    // ===================================================================

    private void crearSolicitudDeCompra(Requerimiento req, List<DetalleRequerimiento> detallesParaComprar) {

        // 1. Crear la cabecera
        SolicitudCompra nuevaSolicitud = new SolicitudCompra();
        nuevaSolicitud.setRequerimiento(req);
        nuevaSolicitud.setIdUsuarioSolicitante(req.getIdUsuarioSolicitante()); // O el ID del programador
        nuevaSolicitud.setEstado("Pendiente");
        nuevaSolicitud.setFechaSolicitud(LocalDate.now());
        nuevaSolicitud.setMotivo("Atención de Req. ID: " + req.getId());

        SolicitudCompra solicitudGuardada = solicitudCompraRepository.save(nuevaSolicitud);

        // 2. Lógica para buscar un proveedor por defecto (simple)
        // (En el mundo real, esta lógica sería más compleja)
        Proveedor proveedorPorDefecto = proveedorRepository.findById(1)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor por defecto ID 1 no encontrado."));

        // 3. Crear los detalles
        List<DetalleSolicitudCompra> detallesSC = detallesParaComprar.stream()
                .map(detalleReq -> {
                    DetalleSolicitudCompra detalleSC = new DetalleSolicitudCompra();
                    detalleSC.setSolicitud(solicitudGuardada);
                    detalleSC.setProducto(detalleReq.getProducto());
                    detalleSC.setDetalleRequerimiento(detalleReq);
                    detalleSC.setCantidadSolicitada(detalleReq.getCantidad());
                    detalleSC.setEstado("Pendiente");
                    detalleSC.setProveedorSeleccionado(proveedorPorDefecto); // Asignación simple
                    detalleSC.setPrecioReferencial(BigDecimal.ZERO); // Compras lo llenará
                    return detalleSC;
                }).collect(Collectors.toList());

        detalleSolicitudCompraRepository.saveAll(detallesSC);
    }


    private void crearOrdenDeDistribucion(Requerimiento req, List<DetalleRequerimiento> detallesParaDistribuir) {

        // 1. Crear la cabecera
        OrdenDistribucion nuevaOrden = new OrdenDistribucion();
        nuevaOrden.setRequerimiento(req);
        nuevaOrden.setIdUsuarioCreacion(req.getIdUsuarioSolicitante());
        nuevaOrden.setEstado("Programada"); // Lista para el módulo de tu amigo
        nuevaOrden.setPrioridad(req.getPrioridad());
        nuevaOrden.setFechaDistribucion(LocalDate.now());

        OrdenDistribucion ordenGuardada = ordenDistribucionRepository.save(nuevaOrden);

        // 2. Implementar la lógica FEFO (First-Expiry, First-Out)
        List<DetalleOrdenDistribucion> detallesODAGuardar = new ArrayList<>();

        for (DetalleRequerimiento detalleReq : detallesParaDistribuir) {

            // a. Consultar los lotes disponibles, ordenados por vencimiento
            List<VwStockActual> lotes = stockRepository.findByIdProductoOrderByFechaVencimientoAsc(detalleReq.getProducto().getId());

            int cantidadPendiente = detalleReq.getCantidad();

            for (VwStockActual lote : lotes) {
                if (cantidadPendiente <= 0) break; // Ya completamos este item

                // b. Calcular cuánto sacar de este lote
                int cantidadDeEsteLote = Math.min(cantidadPendiente, lote.getStockActual());

                // c. Crear un detalle de orden POR CADA LOTE que usamos
                DetalleOrdenDistribucion detalleOD = new DetalleOrdenDistribucion();
                detalleOD.setOrdenDistribucion(ordenGuardada);
                detalleOD.setProducto(detalleReq.getProducto());
                detalleOD.setIdLote(lote.getIdLote()); // ¡Importante! Guardamos el ID del lote
                detalleOD.setCantidad(cantidadDeEsteLote);
                detalleOD.setEstadoEntrega("Pendiente");

                detallesODAGuardar.add(detalleOD);

                cantidadPendiente -= cantidadDeEsteLote;
            }
        }

        // 3. Guardar todos los detalles de lote
        detalleOrdenDistribucionRepository.saveAll(detallesODAGuardar);
    }
}