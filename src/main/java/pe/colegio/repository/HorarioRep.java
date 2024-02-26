package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Horario;

public interface HorarioRep extends JpaRepository<Horario, Integer> {	
}
