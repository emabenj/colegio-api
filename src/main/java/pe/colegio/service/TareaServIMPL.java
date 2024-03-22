package pe.colegio.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Curso;
import pe.colegio.entity.Tarea;
import pe.colegio.repository.TareaRep;

@Service
public class TareaServIMPL implements TareaServ {
	@Autowired
	private TareaRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Tarea buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR TAREAS
	@Override @Transactional(readOnly = true)
	public Collection<Tarea> listar() {
		Collection<Tarea> tareas = repository.findAll();
		return tareas;
	}

	// AGREGAR TAREA
	@Override @Transactional
	public void agregar(Tarea tarea) {
//		Set<Curso> cursos = tarea.getItemsCurso();
		repository.save(tarea);
	}

	// ACTUALIZAR TAREA
	@Override @Transactional
	public void actualizar(Tarea tarea) {
		repository.save(tarea);
	}

	// ELIMINAR TAREA
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}