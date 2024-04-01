package pe.colegio.service;

import java.util.Collection;
import pe.colegio.entity.Notificacion;

public interface NotificacionServ {
	public abstract Notificacion buscarPorId(Integer id);
	public abstract Collection<Notificacion> listar(Integer usuarioId);
	
	public abstract Notificacion agregar(Notificacion notificacion, String tipo);
	public abstract void actualizar(Notificacion notificacion);
	public abstract void eliminar(Integer id);
}
