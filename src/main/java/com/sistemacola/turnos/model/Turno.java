package com.sistemacola.turnos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sistemacola.turnos.model.Paciente;
import java.time.LocalDateTime;

@Entity
@Table(name = "Turnos") // nombre de la tabla en SQL Server
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;        // Número de turno

    @Column(nullable = false, length = 100)
    private String area;           // Área: Emergencia, Caja, etc.

    @Column(nullable = false, length = 20)
    private String estado;         // EN_ESPERA, ATENDIDO, CANCELADO

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    // Relación con Paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id") // columna FK en la tabla turnos
    private Paciente paciente;

    // ===============================
    // NUEVOS CAMPOS PARA REASIGNACIÓN
    // ===============================

    // último motivo de reasignación (puede ser null si nunca se ha reasignado)
    @Column(length = 255)
    private String motivoReasignacion;

    // fecha/hora de la última reasignación (null si nunca se ha reasignado)
    private LocalDateTime fechaReasignacion;
}

