package com.farmaceutica.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "vw_stock_actual", schema = "farmacia_clean")
public class VwStockActual {

    @Id // <-- Ahora la vista SÍ tiene esta columna
    @Column(name = "id_inventario")
    private Integer idInventario;

    @Column(name = "id_almacen") // <-- Añadido
    private Integer idAlmacen;

    @Column(name = "id_lote") // <-- Añadido
    private Integer idLote;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "nombre_almacen")
    private String nombreAlmacen;

    @Column(name = "numero_lote")
    private String numeroLote;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "stock_actual")
    private Integer stockActual;

    @Column(name = "estado_lote")
    private String estadoLote;

    @Column(name = "condiciones_almacenamiento")
    private String condicionesAlmacenamiento;

    // (Añade cualquier otra columna de la vista que necesites)
}