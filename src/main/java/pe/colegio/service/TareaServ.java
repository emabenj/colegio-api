package pe.colegio.service;

import java.util.Collection;
import pe.colegio.entity.Tarea;

public interface TareaServ {
	public abstract Tarea buscarPorId(Integer id);
	public abstract Collection<Tarea> listar();
	
	public abstract void agregar(Tarea tarea);
	public abstract void actualizar(Tarea tarea);
	public abstract void eliminar(Integer id);
}
