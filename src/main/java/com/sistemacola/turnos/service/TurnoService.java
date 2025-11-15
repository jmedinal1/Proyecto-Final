package com.sistemacola.turnos.service;

import com.sistemacola.turnos.model.Turno;
import com.sistemacola.turnos.repository.TurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public Turno crearTurno(Turno turno) {
        if (turno.getFechaCreacion() == null) {
            turno.setFechaCreacion(LocalDateTime.now());
        }
        if (turno.getEstado() == null || turno.getEstado().isBlank()) {
            turno.setEstado("EN_ESPERA");
        }
        return turnoRepository.save(turno);
    }

    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    public List<Turno> obtenerTurnosEnEsperaPorArea(String area) {
        return turnoRepository.findByAreaAndEstadoOrderByNumeroAsc(area, "EN_ESPERA");
    }

    public Optional<Turno> buscarPorId(Long id) {
        return turnoRepository.findById(id);
    }

    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }

    // 🔹 Actualizar estado (ya lo estábamos usando)
    public Optional<Turno> actualizarEstado(Long id, String nuevoEstado) {
        return turnoRepository.findById(id).map(turno -> {
            turno.setEstado(nuevoEstado);
            return turnoRepository.save(turno);
        });
    }

    // 🔹 NUEVO: listar solo turnos activos (EN_ESPERA)
    public List<Turno> listarActivos() {
        return turnoRepository.findByEstadoOrderByFechaCreacionAsc("EN_ESPERA");
    }

    // 🔹 NUEVO: obtener siguiente turno EN_ESPERA por área (sin cambiar estado)
    public Optional<Turno> obtenerSiguienteTurnoPorArea(String area) {
        return turnoRepository.findFirstByAreaAndEstadoOrderByNumeroAsc(area, "EN_ESPERA");
    }

    // 🔹 NUEVO: llamar al siguiente turno (lo marca como ATENDIDO)
    public Optional<Turno> llamarSiguienteTurno(String area) {
        return obtenerSiguienteTurnoPorArea(area).map(turno -> {
            turno.setEstado("ATENDIDO");
            return turnoRepository.save(turno);
        });
    }

    // 🔹 NUEVO: resetear todos los turnos (borrar todo)
    public void eliminarTodos() {
        turnoRepository.deleteAll();
    }

    public List<Turno> listarPorPaciente(Long pacienteId) {
        return turnoRepository.findByPacienteIdOrderByFechaCreacionDesc(pacienteId);
    }

    public List<Turno> ultimosLlamados() {
        return turnoRepository.findTop5ByEstadoOrderByFechaCreacionDesc("ATENDIDO");
    }

    public Map<String, Object> estadisticasGenerales() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalEnEspera", turnoRepository.countByEstado("EN_ESPERA"));
        data.put("totalAtendidos", turnoRepository.countByEstado("ATENDIDO"));
        data.put("totalCancelados", turnoRepository.countByEstado("CANCELADO"));
        data.put("totalTurnos", turnoRepository.count());

        // calcular turnos del día (00:00:00 a 23:59:59)
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atStartOfDay();
        LocalDateTime fin = hoy.atTime(LocalTime.MAX);

        int turnosHoy = turnoRepository.findByFechaCreacionBetween(inicio, fin).size();
        data.put("turnosDeHoy", turnosHoy);

        return data;
    }

    public Map<String, Object> estadisticasPorArea() {
        Map<String, Object> data = new HashMap<>();

        String[] areas = {"EMERGENCIA", "CONSULTA_EXTERNA", "LABORATORIO"};

        for (String area : areas) {
            data.put(area, turnoRepository.countByArea(area));
        }

        return data;
    }

    public Map<String, Object> estadisticasPorPrioridad() {
        Map<String, Object> data = new HashMap<>();

        data.put("prioridad1", turnoRepository.countByPaciente_Prioridad(1));
        data.put("prioridad2", turnoRepository.countByPaciente_Prioridad(2));
        data.put("prioridad3", turnoRepository.countByPaciente_Prioridad(3));

        return data;
    }

    // 🔹 NUEVO: Reasignar un turno a otra área y guardar motivo + fecha
    public Turno reasignarTurno(Long turnoId, String nuevaArea, String motivo) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + turnoId));

        turno.setArea(nuevaArea);
        turno.setMotivoReasignacion(motivo);
        turno.setFechaReasignacion(LocalDateTime.now());

        return turnoRepository.save(turno);
    }
}



