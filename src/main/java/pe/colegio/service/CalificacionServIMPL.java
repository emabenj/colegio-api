package pe.colegio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Calificacion;
import pe.colegio.repository.CalificacionRep;

@Service
public class CalificacionServIMPL implements CalificacionServ{
	@Autowired
	private CalificacionRep repository;
	
	public CalificacionServIMPL() {}

	// BUSCAR NOTAS
	@Override @Transactional(readOnly = true)
	public Calificacion buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// BUSCAR NOTAS by estudianteId, cursoId
	@Override @Transactional(readOnly = true)
	public Calificacion buscarPorEstudianteYCurso(Integer estudianteId, Integer cursoId) {
		return repository.findByAlCurIds(estudianteId, cursoId);
	}

	// AGREGAR CONJUNTO DE NOTAS
	@Override @Transactional
	public void agregar(Calificacion calificacion) {
		repository.save(calificacion);
	}

	// ACTUALIZAR CONJUNTO DE NOTAS
	@Override @Transactional
	public void actualizar(Calificacion calificacion) {
		repository.save(calificacion);
	}

	// ELIMINAR CONJUNTO DE NOTAS
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}
