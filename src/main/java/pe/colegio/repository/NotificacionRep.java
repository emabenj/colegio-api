package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.colegio.entity.Notificacion;

public interface NotificacionRep extends JpaRepository<Notificacion, Integer> {
	public Collection<Notificacion> findByUsuarios_UsuarioId(Integer usuarioId);
//	public Collection<Notificacion> findByFechaPublicacionBefore(LocalDate fecha);
	public void deleteByEstado(String estado);
}