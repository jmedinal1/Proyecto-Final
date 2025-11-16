package com.sistemacola.turnos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data   // ? ESTO genera getters/setters automáticamente
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String rol;
}

