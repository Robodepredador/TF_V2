package com.farmaceutica.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departamentos", schema = "farmacia_clean")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento", nullable = false)
    private Integer id;

    @Column(name = "nombre_departamento", nullable = false, length = 100)
    private String nombreDepartamento;

    // (Aquí puedes añadir la relación @ManyToOne a 'Area' si lo necesitas)
}