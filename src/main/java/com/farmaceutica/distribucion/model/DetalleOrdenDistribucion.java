package com.farmaceutica.distribucion.model;

import com.farmaceutica.core.model.Producto; // Importa de 'core'
// ¡Importante! No enlazamos la entidad Lote, solo guardamos el ID
// para desacoplar el inventario de la distribución.
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "detalle_orden_distribucion", schema = "farmacia_clean")
public class DetalleOrdenDistribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_dist", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden_dist")
    private OrdenDistribucion ordenDistribucion;

    // Aquí guardamos el ID del lote que el programador seleccionó (Lógica FEFO)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ColumnDefault("'Pendiente'")
    @Column(name = "estado_entrega", length = 20)
    private String estadoEntrega;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    // (Omito 'condiciones_transporte', etc. por brevedad)
}