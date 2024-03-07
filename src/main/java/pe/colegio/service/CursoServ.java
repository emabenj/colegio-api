package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Curso;

public interface CursoServ {
	public abstract Curso buscarPorId(Integer id);
	public abstract Collection<Curso> listar(Integer estudianteId, String nivelEducativo);
	
	public abstract void agregar(Curso curso);
	public abstract void actualizar(Curso curso);
	public abstract void eliminar(Integer id);
}
