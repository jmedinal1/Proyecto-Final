package com.sistemacola.turnos.controller;

import com.sistemacola.turnos.model.Paciente;
import com.sistemacola.turnos.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    // Crear paciente
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        Paciente creado = pacienteService.crear(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Listar todos
    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteService.listarTodos();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long id,
            @RequestBody Paciente datos) {

        return pacienteService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = pacienteService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Listar por prioridad
    @GetMapping("/prioridad/{prioridad}")
    public List<Paciente> listarPorPrioridad(@PathVariable int prioridad) {
        return pacienteService.listarPorPrioridad(prioridad);
    }
}

