package com.sistemacola.turnos.controller;

import com.sistemacola.turnos.model.Paciente;
import com.sistemacola.turnos.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")   // ? OJO: debe coincidir con el fetch del HTML
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    // Crear paciente
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        Paciente creado = pacienteService.crear(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Listar todos (por si luego lo necesitas)
    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listar();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        Optional<Paciente> opt = pacienteService.obtenerPorId(id);
        return opt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Long id,
            @RequestBody Paciente pacienteActualizado) {

        Paciente actualizado = pacienteService.actualizar(id, pacienteActualizado);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar (opcional)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = pacienteService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

