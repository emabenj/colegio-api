package pe.colegio.service;

import java.util.Collection;

import pe.colegio.entity.Usuario;

public interface UsuarioServ {
	public abstract Collection<Usuario> listar();
	public abstract Collection<Usuario> listarEliminados();
	public abstract Usuario buscarPorCorreo(String correo);
	public abstract Usuario buscarPorId(Integer usuarioId);

	public abstract void agregar(Usuario usuario);
	public abstract void actualizar(Usuario usuario);
	public abstract void eliminar(String correo);
	public abstract void cambiarEstadoUsuarios(String estado);
}
