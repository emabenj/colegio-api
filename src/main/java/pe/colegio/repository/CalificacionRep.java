package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Calificacion;

public interface CalificacionRep extends JpaRepository<Calificacion, Integer>{
	@Query(value = "SELECT * FROM notas WHERE alumno_id=:alId AND curso_id=:curId", nativeQuery = true)
	Calificacion findByAlCurIds(@Param("alId")Integer alumnoId, @Param("curId")Integer cursoId);
}
