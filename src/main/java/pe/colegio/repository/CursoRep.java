package pe.colegio.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Curso;

public interface CursoRep extends JpaRepository<Curso, Integer> {
	public Boolean existsByCursoId(Integer cursoId);
	public Collection<Curso> findByItemsEstudiante_EstudianteId(Integer estudianteId);
	public Collection<Curso> findByItemsEstudiante_NivelEducativo(Character nivel);
}
