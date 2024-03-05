package pe.colegio.service;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Rol_Usuario;
import pe.colegio.entity.Usuario;
import pe.colegio.repository.DocenteRep;
import pe.colegio.repository.Rol_UsuarioRep;
import pe.colegio.repository.UsuarioRep;

@Service
public class DocenteServIMPL implements DocenteServ{
	@Autowired
	private DocenteRep repository;
	@Autowired
	private UsuarioRep usuarioRepository;
	@Autowired
	private Rol_UsuarioRep rolesRepository;
	
	//LISTAR DOCENTES
	@Override @Transactional(readOnly = true)
	public Collection<Docente> listar(Integer cursoId, Integer estudianteId) {
		Collection<Docente> docentes = 
				cursoId != null ? 
						repository.findByCurso_CursoId(cursoId) : estudianteId == null ? 
								repository.findAll() : repository.findByCurso_ItemsEstudiante_EstudianteId(estudianteId);
		return docentes;
	}
	//BUSCAR DOCENTE POR CORREO
	@Override @Transactional(readOnly = true)
	public Docente buscarPorCorreo(String correo) {
		return repository.findByCorreo(correo).orElse(null);
	}
	//BUSCAR DOCENTE POR ID
	@Override @Transactional(readOnly = true)
	public Docente buscarPorId(Integer correoId) {
		return repository.findById(correoId).orElse(null);
	}	
//	//AGREGAR DOCENTE
	@Override @Transactional
	public Docente agregar(Docente docente) {
		Usuario newUsuario = new Usuario();
		newUsuario.setDocente(docente);
		newUsuario.setContrasena(new BCryptPasswordEncoder().encode(newUsuario.getContrasena()));

		Rol_Usuario roles = rolesRepository.findById(2).orElse(null); // ROL DOCENTE
		newUsuario.setItemsRole(Collections.singleton(roles));
		
		docente.setUsuario(usuarioRepository.save(newUsuario));
		return repository.save(docente);
	}
//	//ACTUALIZAR DOCENTE
	@Override @Transactional
	public void actualizar(Docente docente) {
		Usuario usuarioDocente = usuarioRepository.findByEmail(docente.getCorreo()).orElse(null);
		if (usuarioDocente != null) {
			usuarioDocente.setEmail(docente.getCorreo());
			usuarioDocente.setTelefono(docente.getTelefono());
			usuarioRepository.save(usuarioDocente);
		}
		Docente updateDocente = buscarPorCorreo(docente.getCorreo());
		updateDocente.setCorreo(docente.getCorreo());
		updateDocente.setTelefono(docente.getTelefono());
		updateDocente.setDireccion(docente.getDireccion());
		repository.save(updateDocente);
	}
//	//ELIMINAR DOCENTE
	@Override @Transactional
	public void eliminar(Integer docenteId) {
//		Docente docente = buscarPorId(docenteId);
//		docente.setEstado(EstadoType.DELETED.name());
//		repository.save(docente);
	}
	//FILTRAR by Curso, Nombre, Apellidos, Sexo o FechaRegistro 
//	@Override @Transactional(readOnly = true)
//	public Collection<Docente> filtrarByInfo(Curso curso, Docente docente) {
//		String apellidos = docente.getApellidoP() + " " + docente.getApellidoM();
//		return repository.findByFiltrosCC(curso.getCursoId(), docente.getNombres(), apellidos, docente.getSexo().toString(),
//				docente.getfRegistro(), curso.getNiv_educativo(), curso.getTurno());
//	}
}