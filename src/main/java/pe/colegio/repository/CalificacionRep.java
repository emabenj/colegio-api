package pe.colegio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Calificacion;

public interface CalificacionRep extends JpaRepository<Calificacion, Integer>{
//	@Query(value = "SELECT * FROM calificaciones WHERE estudiante_id=:alId AND curso_id=:curId", nativeQuery = true)
	public Calificacion findByEstudiante_EstudianteIdAndCurso_CursoId(Integer estudianteId, Integer cursoId);
}
