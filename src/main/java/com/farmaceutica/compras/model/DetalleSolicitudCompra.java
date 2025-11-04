package com.farmaceutica.compras.model;

import com.farmaceutica.core.model.Producto; // Importa de 'core'
import com.farmaceutica.core.model.Proveedor; // Importa de 'core'
import com.farmaceutica.programacion.model.DetalleRequerimiento; // Importa de 'programacion'
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "detalle_solicitud_compra", schema = "farmacia_clean")
public class DetalleSolicitudCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_solicitud", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private SolicitudCompra solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_requerimiento")
    private DetalleRequerimiento detalleRequerimiento;

    @Column(name = "cantidad_solicitada", nullable = false)
    private Integer cantidadSolicitada;

    @ColumnDefault("0")
    @Column(name = "cantidad_aprobada")
    private Integer cantidadAprobada;

    // El programador asigna un proveedor sugerido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor_seleccionado", nullable = false)
    private Proveedor proveedorSeleccionado;

    @Column(name = "precio_referencial", precision = 12, scale = 2)
    private BigDecimal precioReferencial;

    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", length = 20)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;
}