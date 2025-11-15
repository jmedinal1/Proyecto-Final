package com.sistemacola.turnos.service;

import com.sistemacola.turnos.model.Paciente;
import com.sistemacola.turnos.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    // Crear paciente
    public Paciente crear(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // Listar todos los pacientes
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    // Buscar por ID
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    // Actualizar paciente
    public Optional<Paciente> actualizar(Long id, Paciente datos) {
        return pacienteRepository.findById(id).map(existing -> {
            existing.setNombre(datos.getNombre());
            existing.setSintomas(datos.getSintomas());
            existing.setPrioridad(datos.getPrioridad());
            return pacienteRepository.save(existing);
        });
    }

    // Eliminar paciente
    public boolean eliminar(Long id) {
        return pacienteRepository.findById(id).map(p -> {
            pacienteRepository.delete(p);
            return true;
        }).orElse(false);
    }

    // Listar por prioridad (1 = alta, 2 = media, 3 = baja, por ejemplo)
    public List<Paciente> listarPorPrioridad(int prioridad) {
        return pacienteRepository.findByPrioridadOrderByNombreAsc(prioridad);
    }
}

