package com.farmaceutica.programacion.model;

import com.farmaceutica.core.model.Producto; // <-- Importa desde core
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter // <-- Soluciona 'getProducto()' y 'getCantidad()'
@Setter
@Entity
@Table(name = "detalle_requerimiento", schema = "farmacia_clean")
public class DetalleRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_req", nullable = false)
    private Integer id;

    // Relación al padre (en su propio módulo)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_requerimiento", nullable = false)
    private Requerimiento requerimiento; // <-- (Usando el refactor)

    // Relación al CORE
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto; // <-- (Usando el refactor)

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ColumnDefault("0")
    @Column(name = "cantidad_atendida")
    private Integer cantidadAtendida;

    @Column(name = "observacion")
    private String observacion;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;
}