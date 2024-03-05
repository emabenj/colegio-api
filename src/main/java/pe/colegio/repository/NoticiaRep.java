package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Noticia;

public interface NoticiaRep extends JpaRepository<Noticia, Integer> {
	public Collection<Noticia> findByOrderByFechaPublicacionDesc();
	public Collection<Noticia> findByFechaPublicacionBefore(LocalDate fecha);
	public void deleteByFechaPublicacionBefore(LocalDate fecha);
}
