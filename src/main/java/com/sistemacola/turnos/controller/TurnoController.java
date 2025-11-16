package com.sistemacola.turnos.controller;

import com.sistemacola.turnos.model.Turno;
import com.sistemacola.turnos.service.PacienteService;
import com.sistemacola.turnos.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;
    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Turno turno) {

        // Si viene un paciente con id, validamos que exista
        if (turno.getPaciente() != null && turno.getPaciente().getId() != null) {
            Long pacienteId = turno.getPaciente().getId();

            var pacienteOpt = pacienteService.obtenerPorId(pacienteId);
            if (pacienteOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El paciente con id " + pacienteId + " no existe.");
            }

            // Reemplazamos por el paciente real de BD
            turno.setPaciente(pacienteOpt.get());
        }

        Turno creado = turnoService.crearTurno(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<Turno> listarTodos() {
        return turnoService.listarTodos();
    }

    @GetMapping("/area/{area}")
    public List<Turno> listarPorArea(@PathVariable String area) {
        return turnoService.obtenerTurnosEnEsperaPorArea(area);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerPorId(@PathVariable Long id) {
        return turnoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Ya lo tenías: actualizar estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<Turno> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String valor) {

        return turnoService.actualizarEstado(id, valor)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 NUEVO: listar solo turnos activos (EN_ESPERA)
    @GetMapping("/activos")
    public List<Turno> listarActivos() {
        return turnoService.listarActivos();
    }

    // 🔹 NUEVO: obtener siguiente turno EN_ESPERA por área (no cambia estado)
    @GetMapping("/siguiente/{area}")
    public ResponseEntity<Turno> obtenerSiguientePorArea(@PathVariable String area) {
        return turnoService.obtenerSiguienteTurnoPorArea(area)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 NUEVO: llamar siguiente turno por área (lo marca ATENDIDO)
    @PostMapping("/llamar/{area}")
    public ResponseEntity<Turno> llamarSiguiente(@PathVariable String area) {
        return turnoService.llamarSiguienteTurno(area)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 NUEVO: resetear todos los turnos (borrar todo)
    @DeleteMapping
    public ResponseEntity<Void> eliminarTodos() {
        turnoService.eliminarTodos();
        return ResponseEntity.noContent().build();
    }

    // Listar turnos de un paciente
    @GetMapping("/paciente/{pacienteId}")
    public List<Turno> listarPorPaciente(@PathVariable Long pacienteId) {
        return turnoService.listarPorPaciente(pacienteId);
    }

    // Últimos turnos llamados (para la pantalla de sala de espera)
    @GetMapping("/llamados/ultimos")
    public List<Turno> ultimosLlamados() {
        return turnoService.ultimosLlamados();
    }

    @GetMapping("/estadisticas/general")
    public Map<String, Object> generalDashboard() {
        return turnoService.estadisticasGenerales();
    }

    @GetMapping("/estadisticas/por-area")
    public Map<String, Object> porAreaDashboard() {
        return turnoService.estadisticasPorArea();
    }

    @GetMapping("/estadisticas/prioridad")
    public Map<String, Object> porPrioridadDashboard() {
        return turnoService.estadisticasPorPrioridad();
    }

    // 🔹 NUEVO: Reasignar turno a otra área con motivo
    @PutMapping("/{id}/reasignar")
    public ResponseEntity<Turno> reasignarTurno(
            @PathVariable Long id,
            @RequestBody ReasignarTurnoRequest request) {

        // Validaciones básicas
        if (request.getNuevaArea() == null || request.getNuevaArea().isBlank()
                || request.getMotivo() == null || request.getMotivo().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Turno actualizado = turnoService.reasignarTurno(
                    id,
                    request.getNuevaArea(),
                    request.getMotivo()
            );
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            // Por ejemplo, si el turno no existe
            return ResponseEntity.notFound().build();
        }
    }

}


