package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Horario;

public interface HorarioRep extends JpaRepository<Horario, Integer> {
	public Collection<Horario> findByFechaClaseAndCurso_CursoId(LocalDate fechaClase, Integer cursoId);
}