package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Tarea;

public interface TareaRep extends JpaRepository<Tarea, Integer> {	
}
