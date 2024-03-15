package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Estudiante;

public interface EstudianteServ {
	public abstract Estudiante buscarPorId(Integer id, Integer dni);
	public abstract Collection<Estudiante> listar(Integer cursoId, String nivel, Integer grado, String seccion);
	
//	public abstract void agregar(Estudiante estudiante);
	public abstract void actualizar(Estudiante estudiante);
//	public abstract void eliminar(Integer id);
	
//	public abstract Integer cantAlumnos(String ned, String grado, String seccion, String turno);
}
