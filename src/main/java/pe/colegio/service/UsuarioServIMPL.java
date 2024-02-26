package pe.colegio.service;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Docente;
import pe.colegio.entity.Usuario;
import pe.colegio.repository.DocenteRep;
import pe.colegio.repository.UsuarioRep;
import pe.colegio.util.EstadoType;

@Service
public class UsuarioServIMPL implements UsuarioServ, UserDetailsService{
	@Autowired
	private UsuarioRep repository;
	@Autowired
	private DocenteRep docenteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado con el correo ingresado: "+username));
		
		Collection<GrantedAuthority> authorities = usuario.getItemsRole().stream()
				.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre())).collect(Collectors.toList());
		return new User(usuario.getEmail(), usuario.getContrasena(), authorities);
	}
	//BUSCAR POR CORREO
	@Override @Transactional(readOnly = true)
	public Usuario buscarPorCorreo(String correo) {
		Usuario usuario = repository.findByEmail(correo).orElse(null);
		return usuario;
	}
	//BUSCAR POR ID
	@Override
	public Usuario buscarPorId(Integer usuarioId) {
		Usuario usuario = repository.findById(usuarioId).orElse(null);
		return usuario;
	}
	//AGREGAR USUARIO
	@Override
	public void agregar(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}
	//ACTUALIZAR USUARIO
	@Override @Transactional
	public void actualizar(Usuario usuario) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		String passwordCrypt = bCrypt.encode(usuario.getContrasena());
		Usuario updateUsuario = repository.findByEmail(usuario.getEmail()).orElse(null);
		updateUsuario.setContrasena(passwordCrypt);
		repository.save(updateUsuario);
	}
	//ELIMINAR USUARIO
	@Override @Transactional
	public void eliminar(String correo) {
//		Usuario usuario = buscarPorCorreo(user);
//		
//		usuario.setEstado(EstadoType.DELETED.name());		
//		if (docente != null) { 
//			docente.setEstado(EstadoType.DELETED.name());
//			docenteRepository.save(docente);
//		}
//		
//		repository.save(usuario);
	}
	// LISTAR USUARIOS (Docentes)
	@Override @Transactional(readOnly = true)
	public Collection<Usuario> listar() {
		return repository.findAll().stream().filter(u -> !u.getEstado().equals(EstadoType.DELETED.name()) 
				&& u.getItemsRole().stream().noneMatch(itemsRole ->itemsRole.getRoleId() == 2)).toList();
	}
	// LISTAR USUARIOS (Docentes) + Eliminados
	@Override @Transactional(readOnly = true)
	public Collection<Usuario> listarEliminados() {
		return repository.findAll().stream().filter(u -> u.getItemsRole().stream().noneMatch(itemsRole ->itemsRole.getRoleId() == 2)).toList();
	}
	// DESACTIVAR ESTADOS
	@Override
	public void cambiarEstadoUsuarios(String estado) {
		Collection<Usuario> usuarios = repository.findAll();
	    for (Usuario usuario : usuarios) { usuario.setEstado(estado); }
	    repository.saveAll(usuarios);
	}
}