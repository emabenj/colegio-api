package pe.colegio.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Notificacion;
import pe.colegio.entity.Usuario;
import pe.colegio.repository.UsuarioRep;
import pe.colegio.repository.NotificacionRep;
import pe.colegio.util.EstadoType;

@Service
public class NotificacionServIMPL implements NotificacionServ {
	@Autowired
	private NotificacionRep repository;
	@Autowired
	private UsuarioRep usuarioRep;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Notificacion buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR NOTIFICACIONS
	@Override @Transactional
	public Collection<Notificacion> listar(Integer usuarioId) {		
		repository.deleteByEstado(EstadoType.BORRAR.name());
		Collection<Notificacion> notificaciones = repository.findByUsuarios_UsuarioId(usuarioId);
		return notificaciones;
	}

	// AGREGAR DE NOTIFICACION
	@Override @Transactional
	public Notificacion agregar(Notificacion notificacion, String tipo) {
		System.out.println(notificacion.getEstado());
		notificacion.setNotificacionId(null);
		List<Integer> idsUsuarios = notificacion.getUsuarios().stream().map(u->u.getUsuarioId()).collect(Collectors.toList());
		List<Usuario> usuarios = usuarioRep.findAllById(idsUsuarios);
		notificacion.setUsuarios(usuarios);
		if(tipo != null && !tipo.isEmpty()) { notificacion.setTipo(tipo); }
		return repository.save(notificacion);
	}

	// ACTUALIZAR NOTIFICACION
	@Override @Transactional
	public void actualizar(Notificacion notificacion) {
		Notificacion update = buscarPorId(notificacion.getNotificacionId());
		update.setDescripcion(notificacion.getDescripcion());
		update.setTitulo(notificacion.getTitulo());
		update.setTipo(notificacion.getTipo());
		repository.save(update);
	}

	// ELIMINAR NOTIFICACION
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}