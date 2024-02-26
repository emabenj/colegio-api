package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Curso;
import pe.colegio.repository.CursoRep;

@Service
public class CursoServIMPL implements CursoServ {
	@Autowired
	private CursoRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Curso buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR CURSOS
	@Override @Transactional(readOnly = true)
	public Collection<Curso> listar(Integer estudianteId) {
		Collection<Curso> cursos = estudianteId != null ? repository.findByItemsEstudiante_EstudianteId(estudianteId) : repository.findAll();
		return cursos;
	}

	// AGREGAR CURSO
	@Override @Transactional
	public void agregar(Curso curso) {
		curso.setCursoId(null);
		repository.save(curso);
	}

	// ACTUALIZAR CURSO
	@Override @Transactional
	public void actualizar(Curso curso) {
		repository.save(curso);
	}

	// ELIMINAR CURSO
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}