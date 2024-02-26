package pe.colegio.service;

import java.util.Collection;
import pe.colegio.entity.Mensaje;

public interface MensajeServ {
	public abstract Mensaje buscarPorId(Integer id);
	public abstract Collection<Mensaje> listar();
	
	public abstract void agregar(Mensaje mensaje);
	public abstract void actualizar(Mensaje mensaje);
	public abstract void eliminar(Integer id);
}
