package pe.colegio.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Estudiante;

public interface EstudianteRep extends JpaRepository<Estudiante, Integer>{
	public Collection<Estudiante> findByItemsCurso_CursoId(Integer cursoId);
	public Estudiante findByDni(Integer dni);
}
