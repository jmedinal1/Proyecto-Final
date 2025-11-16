package com.sistemacola.turnos.repository;

import com.sistemacola.turnos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

