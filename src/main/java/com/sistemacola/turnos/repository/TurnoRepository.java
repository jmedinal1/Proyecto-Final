package com.sistemacola.turnos.repository;

import com.sistemacola.turnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;


@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // Ya lo tenías
    List<Turno> findByAreaAndEstadoOrderByNumeroAsc(String area, String estado);

    // Nuevos:

    // Todos los turnos por estado ordenados por fecha de creación (para activos)
    List<Turno> findByEstadoOrderByFechaCreacionAsc(String estado);

    // Primer turno EN_ESPERA de un área, ordenado por número
    Optional<Turno> findFirstByAreaAndEstadoOrderByNumeroAsc(String area, String estado);

    List<Turno> findByPacienteIdOrderByFechaCreacionDesc(Long pacienteId);

    // Últimos 5 turnos llamados (ATENDIDO), ordenados del más reciente al más viejo
    List<Turno> findTop5ByEstadoOrderByFechaCreacionDesc(String estado);

    // 🔹 NUEVOS para estadísticas:

    long countByEstado(String estado);

    long countByArea(String area);

    long countByPaciente_Prioridad(int prioridad);

    // turnos del día usando rango de fechas
    List<Turno> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);


}
