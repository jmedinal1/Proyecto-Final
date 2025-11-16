package com.sistemacola.turnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paciente")
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer prioridad;  // 1 = Alta, 2 = Media, 3 = Baja

    @Column(length = 500)
    private String sintomas;
}




