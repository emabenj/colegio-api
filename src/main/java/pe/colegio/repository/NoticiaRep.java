package pe.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Noticia;

public interface NoticiaRep extends JpaRepository<Noticia, Integer> {	
}
