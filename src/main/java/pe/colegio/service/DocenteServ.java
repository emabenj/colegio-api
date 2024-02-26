package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Curso;
import pe.colegio.entity.Docente;

public interface DocenteServ {
	public abstract Docente buscarPorId(Integer docenteId);
	public abstract Docente buscarPorCorreo(String correo);
	public abstract Collection<Docente> listar(Integer cursoId, Integer estudianteId);
//	
	public abstract void agregar(Docente docente);
	public abstract void actualizar(Docente docente);
	public abstract void eliminar(Integer docenteId);
	
//	public abstract Collection<Docente> filtrarByInfo(Curso curso, Docente docente);
}
