package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Mensaje;

public interface MensajeRep extends JpaRepository<Mensaje, Integer> {	
}
