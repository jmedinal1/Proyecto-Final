package com.sistemacola.turnos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 500)
    private String sintomas;

    @Column(nullable = false)
    private int prioridad; // 1 = alta, 2 = media, 3 = baja (por ejemplo)
}


