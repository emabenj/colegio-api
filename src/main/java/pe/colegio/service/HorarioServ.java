package pe.colegio.service;

import java.time.LocalDate;
import java.util.Collection;
import pe.colegio.entity.Horario;

public interface HorarioServ {
	public abstract Collection<Horario> listar(LocalDate fechaClase, Collection<Integer> cursoIds);
	public abstract Horario buscarPorId(Integer id);
	
	public abstract void agregar(Horario horario);
	public abstract void actualizar(Horario horario);
	public abstract void eliminar(Integer id);
}
