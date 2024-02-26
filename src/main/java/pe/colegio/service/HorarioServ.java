package pe.colegio.service;

import java.util.Collection;
import pe.colegio.entity.Horario;

public interface HorarioServ {
	public abstract Horario buscarPorId(Integer id);
	public abstract Collection<Horario> listar();
	
	public abstract void agregar(Horario horario);
	public abstract void actualizar(Horario horario);
	public abstract void eliminar(Integer id);
}
