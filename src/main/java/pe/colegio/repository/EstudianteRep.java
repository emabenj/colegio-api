package pe.colegio.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.colegio.entity.Estudiante;

public interface EstudianteRep extends JpaRepository<Estudiante, Integer>{
	public Collection<Estudiante> findByItemsCurso_CursoId(Integer cursoId);
	public Collection<Estudiante> findByItemsCurso_CursoIdAndGrado(Integer cursoId, Integer grado);
	public Collection<Estudiante> findByItemsCurso_CursoIdAndGradoAndSeccion(Integer cursoId, Integer grado, Character seccion);
	
	@Query("SELECT e FROM Estudiante e WHERE SIZE(e.itemsApoderado) < :maxApoderados")
    public Collection<Estudiante> findByItemsApoderadoSizeLessThan(Integer maxApoderados);
	public Estudiante findByDni(Integer dni);	
}
