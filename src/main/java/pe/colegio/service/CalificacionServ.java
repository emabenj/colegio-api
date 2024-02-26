package pe.colegio.service;

import pe.colegio.entity.Calificacion;

public interface CalificacionServ {
	public abstract Calificacion buscarPorId(Integer id);
	public abstract Calificacion buscarPorEstudianteYCurso(Integer alumnoId, Integer cursoId);
	
	public abstract void agregar(Calificacion calificacion);
	public abstract void actualizar(Calificacion calificacion);
	public abstract void eliminar(Integer id);
}
