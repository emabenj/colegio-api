package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Curso;
import pe.colegio.entity.Estudiante;

public interface AsistenciaRep extends JpaRepository<Asistencia, Integer>{
	
	@Query("SELECT a FROM Asistencia a JOIN a.itemsEstudiante e WHERE a.fecha = :fecha AND e IN :estudiantes")
	public List<Asistencia> findByFechaAndEstudiantes(@Param("fecha") LocalDate fecha, @Param("estudiantes") Collection<Estudiante> estudiantes);
	
	public Asistencia findByFechaAndItemsEstudiante_EstudianteId(Integer estudianteId, LocalDate fecha);
}