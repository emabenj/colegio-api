package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Evento;

public interface EventoRep extends JpaRepository<Evento, Integer> {	
}
