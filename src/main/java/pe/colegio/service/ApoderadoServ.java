package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Apoderado;

public interface ApoderadoServ {
	public abstract Apoderado buscarPorId(Integer apoderadoId);
	public abstract Apoderado buscarPorCorreo(String correo);
	public abstract Collection<Apoderado> listar(Integer alumnoId);
	
	public abstract void agregar(Apoderado apoderado);
	public abstract void actualizar(Apoderado apoderado);
	
//	public abstract Collection<Apoderado> filtrarByInfo(Curso curso, Apoderado Apoderado);
}
