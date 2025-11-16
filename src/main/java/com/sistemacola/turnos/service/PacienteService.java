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

    public Paciente crear(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public boolean eliminar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            return false;
        }
        pacienteRepository.deleteById(id);
        return true;
    }

    public Paciente actualizar(Long id, Paciente datos) {
        Optional<Paciente> opt = pacienteRepository.findById(id);
        if (opt.isEmpty()) return null;

        Paciente p = opt.get();
        p.setNombre(datos.getNombre());
        p.setPrioridad(datos.getPrioridad());
        p.setSintomas(datos.getSintomas());

        return pacienteRepository.save(p);
    }


}


