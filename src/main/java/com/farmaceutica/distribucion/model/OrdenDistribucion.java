package com.farmaceutica.distribucion.model;

import com.farmaceutica.programacion.model.Requerimiento; // Importa de 'programacion'
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orden_distribucion", schema = "farmacia_clean")
public class OrdenDistribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_dist", nullable = false)
    private Integer id;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_distribucion")
    private LocalDate fechaDistribucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requerimiento")
    private Requerimiento requerimiento;

    @Column(name = "id_usuario_creacion")
    private Integer idUsuarioCreacion; // El programador

    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", length = 50)
    private String estado;

    @ColumnDefault("'Normal'")
    @Column(name = "prioridad", length = 20)
    private String prioridad;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;
}