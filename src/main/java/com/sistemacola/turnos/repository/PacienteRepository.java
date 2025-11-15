package com.sistemacola.turnos.repository;

import com.sistemacola.turnos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Ejemplo: buscar pacientes por prioridad (1 = más urgente)
    List<Paciente> findByPrioridadOrderByNombreAsc(int prioridad);
}
