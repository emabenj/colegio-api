package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.colegio.entity.Asistencia;

public interface AsistenciaRep extends JpaRepository<Asistencia, Integer>{
}
