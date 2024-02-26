package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Evento;

public interface EventoServ {
	public abstract Evento buscarPorId(Integer id);
	public abstract Collection<Evento> listar();
	
	public abstract void agregar(Evento evento);
	public abstract void actualizar(Evento evento);
	public abstract void eliminar(Integer id);
}
