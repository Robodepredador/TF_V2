
package com.farmaceutica.compras.service;

import com.farmaceutica.compras.dto.ProveedorListDTO;
import com.farmaceutica.compras.dto.ProveedorRequestDTO;
import com.farmaceutica.compras.dto.ProveedorResponseDTO;
import com.farmaceutica.compras.mapper.ComprasMapper;
import com.farmaceutica.compras.model.Proveedor;
import com.farmaceutica.compras.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ServiceRegistrarProveedor {

    private final ProveedorRepository proveedorRepository;

    public ServiceRegistrarProveedor(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    /* ---------------------------------------------------
     * REGISTRAR PROVEEDOR
     * --------------------------------------------------- */
    public ProveedorResponseDTO registrarProveedor(ProveedorRequestDTO dto) {

        // Validación nombre existente
        if (proveedorRepository.existsByNombreProveedor(dto.nombre())) {
            throw new RuntimeException("El nombre del proveedor ya existe.");
        }

        // Validación RUC existente
        if (proveedorRepository.existsByRuc(dto.ruc())) {
            throw new RuntimeException("El RUC ya pertenece a otro proveedor.");
        }

        // Crear entidad
        Proveedor p = new Proveedor();
        p.setNombreProveedor(dto.nombre());
        p.setRuc(dto.ruc());
        p.setDireccion(dto.direccion());
        p.setTelefono(dto.telefono());
        p.setCorreo(dto.correo());
        p.setEstado(true); // activo por defecto
        p.setFechaCreacion(Instant.now());
        p.setFechaActualizacion(Instant.now());

        // Guardar
        proveedorRepository.save(p);

        return ComprasMapper.toProveedorResponseDTO(p);
    }


    /* ---------------------------------------------------
     * ACTUALIZAR PROVEEDOR
     * --------------------------------------------------- */
    public ProveedorResponseDTO actualizarProveedor(Integer id, ProveedorRequestDTO dto) {

        Proveedor p = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        // Evitar duplicidad en nombre
        proveedorRepository.findByNombreProveedor(dto.nombre())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(e -> { throw new RuntimeException("El nombre ya está registrado por otro proveedor."); });

        // Evitar duplicidad en RUC
        proveedorRepository.findByRuc(dto.ruc())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(e -> { throw new RuntimeException("El RUC ya está registrado por otro proveedor."); });

        // Actualizar campos
        p.setNombreProveedor(dto.nombre());
        p.setRuc(dto.ruc());
        p.setDireccion(dto.direccion());
        p.setTelefono(dto.telefono());
        p.setCorreo(dto.correo());
        p.setFechaActualizacion(Instant.now());

        proveedorRepository.save(p);

        return ComprasMapper.toProveedorResponseDTO(p);
    }


    /* ---------------------------------------------------
     * CONSULTAR PROVEEDOR POR ID
     * --------------------------------------------------- */
    public ProveedorResponseDTO buscarProveedor(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        return ComprasMapper.toProveedorResponseDTO(proveedor);
    }


    /* ---------------------------------------------------
     * LISTAR PROVEEDORES ACTIVOS
     * --------------------------------------------------- */
    public List<ProveedorListDTO> listarProveedoresActivos() {
        return ComprasMapper.toProveedorList(
                proveedorRepository.findByEstadoTrue()
        );
    }


    /* ---------------------------------------------------
     * CAMBIAR ESTADO (activar / desactivar)
     * --------------------------------------------------- */
    public void cambiarEstado(Integer id, boolean nuevoEstado) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado."));

        proveedor.setEstado(nuevoEstado);
        proveedor.setFechaActualizacion(Instant.now());

        proveedorRepository.save(proveedor);
    }

}
