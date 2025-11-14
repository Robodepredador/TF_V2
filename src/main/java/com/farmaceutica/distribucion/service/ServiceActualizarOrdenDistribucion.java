package com.farmaceutica.distribucion.service;

import com.farmaceutica.distribucion.dto.*;
import com.farmaceutica.distribucion.mapper.DistribucionMapper;
import com.farmaceutica.distribucion.model.*;
import com.farmaceutica.distribucion.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceActualizarOrdenDistribucion {

    private final OrdenDistribucionRepository ordenDistribucionRepository;
    private final DetalleOrdenDistribucionRepository detalleOrdenDistribucionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final SeguimientoVehiculoRepository seguimientoVehiculoRepository;
    private final DetalleTransporteRepository detalleTransporteRepository;

    private final DistribucionMapper mapper;

    // -------------------------------------------------------------------
    // 1. ACTUALIZAR ORDEN
    // -------------------------------------------------------------------
    public OrdenDistribucionResponseDTO actualizarOrden(ActualizarEstadoOrdenDistribucionRequestDTO request) {

        OrdenDistribucion orden = ordenDistribucionRepository.findById(request.idOrdenDistribucion())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        orden.setEstado(request.nuevoEstado());
        orden.setFechaActualizacion(java.time.Instant.now());

        ordenDistribucionRepository.save(orden);

        return mapper.toOrdenDistribucionResponseDTO(orden);
    }

    // -------------------------------------------------------------------
    // 2. CONSULTAR ORDEN DISTRIBUCIÓN COMPLETA
    // -------------------------------------------------------------------

    public OrdenDistribucionDetalleResponseDTO consultarOrdenDistribucion(Integer idOrden) {

        OrdenDistribucion orden = ordenDistribucionRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        List<DetalleOrdenDistribucion> detalles = detalleOrdenDistribucionRepository.findByIdOrdenDist(orden);

        List<DetalleOrdenDistribucionDTO> detallesDTO =
                detalles.stream().map(mapper::toDetalleOrdenDistribucionDTO).toList();

        return mapper.toOrdenDistribucionDetalleResponseDTO(orden, detallesDTO);
    }



    // -------------------------------------------------------------------
    // 3. REGISTRAR VEHÍCULO A ORDEN
    // -------------------------------------------------------------------
    public VehiculoResponseDTO registrarVehiculoAOrden(RegistrarVehiculoAOrdenRequestDTO request) {

        OrdenDistribucion orden = ordenDistribucionRepository.findById(request.idOrdenDistribucion())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.idVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!vehiculo.getEstado().equalsIgnoreCase("Disponible")) {
            throw new RuntimeException("El vehículo no está disponible");
        }

        // Asignar vehiculo a todos los detalles de la orden
        List<DetalleOrdenDistribucion> detalles = detalleOrdenDistribucionRepository.findByIdOrdenDist(orden);

        for (DetalleOrdenDistribucion d : detalles) {
            d.setIdVehiculoAsignado(vehiculo);
            detalleOrdenDistribucionRepository.save(d);
        }

        vehiculo.setEstado("Asignado");
        vehiculoRepository.save(vehiculo);

        return mapper.toVehiculoResponseDTO(vehiculo);
    }

    // -------------------------------------------------------------------
    // 4. CONSULTAR DETALLES DE LOTES POR ORDEN
    // -------------------------------------------------------------------
    public List<DetalleOrdenDistribucionDTO> consultarDetalleLotes(Integer idOrden) {

        OrdenDistribucion orden = ordenDistribucionRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        return detalleOrdenDistribucionRepository.findByIdOrdenDist(orden)
                .stream()
                .map(mapper::toDetalleOrdenDistribucionDTO)
                .toList();
    }

    // -------------------------------------------------------------------
    // 5. CONSULTAR VEHÍCULOS DISPONIBLES
    // -------------------------------------------------------------------
    public List<VehiculoDTO> consultarVehiculosDisponibles() {
        return vehiculoRepository.findByEstado("Disponible")
                .stream()
                .map(mapper::toVehiculoDTO)
                .toList();
    }

    // -------------------------------------------------------------------
    // 6. REGISTRAR VEHÍCULO A DETALLE DE LOTE
    // -------------------------------------------------------------------
    public DetalleOrdenDistribucionDTO registrarVehiculoALote(RegistrarVehiculoALoteRequestDTO request) {

        DetalleOrdenDistribucion detalle = detalleOrdenDistribucionRepository.findById(request.idDetalleDistribucion())
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(request.idVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!vehiculo.getEstado().equalsIgnoreCase("Disponible")
                && !vehiculo.getEstado().equalsIgnoreCase("Asignado")) {
            throw new RuntimeException("Vehículo no apto para asignación");
        }

        detalle.setIdVehiculoAsignado(vehiculo);
        detalleOrdenDistribucionRepository.save(detalle);

        return mapper.toDetalleOrdenDistribucionDTO(detalle);
    }
}
