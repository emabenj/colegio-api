package pe.colegio.service;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Apoderado;
import pe.colegio.entity.Rol_Usuario;
import pe.colegio.entity.Usuario;
import pe.colegio.repository.ApoderadoRep;
import pe.colegio.repository.Rol_UsuarioRep;
import pe.colegio.repository.UsuarioRep;

@Service
public class ApoderadoServIMPL implements ApoderadoServ{
	@Autowired
	private ApoderadoRep repository;
	@Autowired
	private UsuarioRep usuarioRepository;
	@Autowired
	private Rol_UsuarioRep rolesRepository;
	
	//LISTAR APODERADOS
	@Override @Transactional(readOnly = true)
	public Collection<Apoderado> listar(Integer alumnoId) {
		Collection<Apoderado> apoderados = alumnoId != null ? repository.findByItemsEstudiante_EstudianteId(alumnoId) : repository.findAll();
		return apoderados;
	}
	//BUSCAR APODERADO POR CORREO
	@Override @Transactional(readOnly = true)
	public Apoderado buscarPorCorreo(String correo) {
		return repository.findByCorreo(correo).orElse(null);
	}
	//BUSCAR APODERADO POR ID
	@Override @Transactional(readOnly = true)
	public Apoderado buscarPorId(Integer correoId) {
		return repository.findById(correoId).orElse(null);
	}	
	//AGREGAR APODERADO
	@Override @Transactional
	public void agregar(Apoderado apoderado) {
		Usuario newUsuario = new Usuario();
		newUsuario.setApoderado(apoderado);
		newUsuario.setContrasena(new BCryptPasswordEncoder().encode(newUsuario.getContrasena()));
		
		Rol_Usuario roles = rolesRepository.findById(3).orElse(null); // ROL APODERADO
		newUsuario.setItemsRole(Collections.singleton(roles));
		
		apoderado.setUsuario(usuarioRepository.save(newUsuario));
		repository.save(apoderado);
	}
	//ACTUALIZAR APODERADO
	@Override @Transactional
	public void actualizar(Apoderado apoderado) {
		Usuario usuarioApoderado = usuarioRepository.findByEmail(apoderado.getCorreo()).orElse(null);
		if (usuarioApoderado != null) {
			usuarioApoderado.setEmail(apoderado.getCorreo());
			usuarioApoderado.setTelefono(apoderado.getTelefono());
			usuarioRepository.save(usuarioApoderado);
		}
		Apoderado updateApoderado = buscarPorCorreo(apoderado.getCorreo());
		updateApoderado.setCorreo(apoderado.getCorreo());
		updateApoderado.setTelefono(apoderado.getTelefono());
		updateApoderado.setDireccion(apoderado.getDireccion());
		repository.save(updateApoderado);
	}
}