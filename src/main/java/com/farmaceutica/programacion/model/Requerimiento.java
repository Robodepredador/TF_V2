package com.farmaceutica.programacion.model;

// Importa la entidad del módulo CORE
import com.farmaceutica.core.model.Departamento;

// (Importa aquí tus otras entidades de otros módulos cuando las necesites)
// import com.farmaceutica.compras.model.SolicitudCompra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter // <-- Soluciona 'getEstado()'
@Setter // <-- Soluciona 'setEstado()'
@Entity
@Table(name = "requerimientos", schema = "farmacia_clean")
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requerimiento", nullable = false)
    private Integer id;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    // Relación al CORE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento; // <-- (Usando el refactor)

    @Column(name = "id_usuario_solicitante", nullable = false)
    private Integer idUsuarioSolicitante; // (Por ahora simple)

    @ColumnDefault("'Media'")
    @Column(name = "prioridad", length = 20)
    private String prioridad;

    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @Column(name = "observacion")
    private String observacion;

    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", length = 50)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    // Relación a su propio módulo (Detalle)
    @OneToMany(mappedBy = "requerimiento")
    private Set<DetalleRequerimiento> detalleRequerimientos = new LinkedHashSet<>();

    // (Relación al módulo 'compras' - prepara el terreno)
    // @OneToMany(mappedBy = "requerimiento")
    // private Set<SolicitudCompra> solicitudCompras = new LinkedHashSet<>();
}