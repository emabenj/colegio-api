package pe.colegio.service;

import java.util.Collection;
import pe.colegio.entity.Noticia;

public interface NoticiaServ {
	public abstract Noticia buscarPorId(Integer id);
	public abstract Collection<Noticia> listar();
	
	public abstract void agregar(Noticia noticia);
	public abstract void actualizar(Noticia noticia);
	public abstract void eliminar(Integer id);
}
