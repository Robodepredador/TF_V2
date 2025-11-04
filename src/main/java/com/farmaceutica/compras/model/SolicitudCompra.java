package com.farmaceutica.compras.model;

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
@Table(name = "solicitud_compra", schema = "farmacia_clean")
public class SolicitudCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requerimiento", nullable = false)
    private Requerimiento requerimiento;

    @Column(name = "id_usuario_solicitante", nullable = false)
    private Integer idUsuarioSolicitante; // El programador

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "motivo")
    private String motivo;

    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", length = 50)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    // ... (omito 'productos_recibidos' y 'fecha_actualizacion' por brevedad)
}