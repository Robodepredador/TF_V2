package com.farmaceutica.almacenamiento.service;

import com.farmaceutica.almacenamiento.model.*;
import com.farmaceutica.almacenamiento.repository.*;
import com.farmaceutica.compras.model.OrdenCompra;
import com.farmaceutica.compras.model.Producto;
import com.farmaceutica.core.model.Usuario;
import com.farmaceutica.core.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceRegistrarLote {

    private final LoteProductoRepository loteRepository;
    private final ProductoRepository productoRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final IncidenciaLoteRepository incidenciaRepository;
    private final InventarioRepository inventarioRepository;
    private final MovimientoInventarioRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlmacenRepository almacenRepository;

    private final AlmacenamientoMapper mapper;

    // ============================================================
    //              REGISTRAR LOTE
    // ============================================================
    @Transactional
    public LoteProductoResponseDTO registrarLote(RegistrarLoteRequestDTO dto) {

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        OrdenCompra ordenCompra = ordenCompraRepository.findById(dto.idOrdenCompra())
                .orElseThrow(() -> new EntityNotFoundException("Orden de compra no encontrada"));

        // Crear entidad lote
        LoteProducto lote = mapper.toLoteEntity(dto, producto, ordenCompra);

        // Guardar lote
        lote = loteRepository.save(lote);

        // ===============================
        // Crear inventario inicial
        // ===============================
        Almacen almacenPrincipal = almacenRepository.findByTipoAlmacen("General")
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No existe almacén principal"));

        Inventario inventario = new Inventario();
        inventario.setIdAlmacen(almacenPrincipal);
        inventario.setIdLote(lote);
        inventario.setStockActual(dto.cantidadInicial());
        inventarioRepository.save(inventario);

        // ===============================
        // Registrar movimiento de entrada
        // ===============================
        MovimientoInventario mov = new MovimientoInventario();
        mov.setTipoMovimiento("ENTRADA");
        mov.setCantidad(dto.cantidadInicial());
        mov.setIdInventario(inventario);
        mov.setTipoReferencia("LOTE");
        mov.setIdReferencia(lote.getId());
        movimientoRepository.save(mov);

        // ===============================
        // Actualizar estado de OrdenCompra
        // ===============================
        ordenCompra.setEstado("Recepcionado Parcialmente");
        ordenCompraRepository.save(ordenCompra);

        return mapper.toLoteResponseDTO(lote);
    }


    // ============================================================
    //              REGISTRAR INCIDENCIA DE LOTE
    // ============================================================
    @Transactional
    public void registrarIncidencia(RegistrarIncidenciaLoteRequestDTO dto) {

        LoteProducto lote = loteRepository.findById(dto.idLote())
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));

        OrdenCompra ordenCompra = dto.idOrdenCompra() != null
                ? ordenCompraRepository.findById(dto.idOrdenCompra())
                .orElse(null)
                : null;

        Usuario usuario = usuarioRepository.findById(dto.idUsuarioRegistro())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        IncidenciaLote incidencia = mapper.toIncidenciaEntity(dto, lote, ordenCompra, usuario);

        incidenciaRepository.save(incidencia);

        // Marcar el lote como observado
        lote.setEstado("Observado");
        loteRepository.save(lote);
    }

}
