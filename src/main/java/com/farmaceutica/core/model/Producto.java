package com.farmaceutica.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos", schema = "farmacia_clean")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private Integer id;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String nombreProducto;

    @Column(name = "codigo_digemid", length = 50)
    private String codigoDigemid;

    @Column(name = "registro_sanitario", length = 50)
    private String registroSanitario;

    // (Añade aquí las relaciones @ManyToOne a TipoProducto y FormaFarmaceutica)
}