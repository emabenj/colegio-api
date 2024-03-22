package pe.colegio.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Calificacion;
import pe.colegio.entity.Curso;
import pe.colegio.entity.Estudiante;
import pe.colegio.repository.CalificacionRep;
import pe.colegio.repository.CursoRep;
import pe.colegio.repository.EstudianteRep;

@Service
public class CalificacionServIMPL implements CalificacionServ{
	@Autowired
	private CalificacionRep repository;
	@Autowired
	private EstudianteRep estudianteRep;
	@Autowired
	private CursoRep cursoRep;
	
	public CalificacionServIMPL() {}

	// BUSCAR NOTAS
	@Override @Transactional(readOnly = true)
	public Calificacion buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// BUSCAR NOTAS by estudianteId, cursoId
	@Override @Transactional
	public Calificacion buscarPorEstudianteYCurso(Integer estudianteId, Integer cursoId) {
		Calificacion calificacion = new Calificacion();
		Calificacion calificacionSearch = repository.findByEstudiante_EstudianteIdAndCurso_CursoId(estudianteId, cursoId);
//		System.out.println("\n" + calificacionSearch.getCalificacionId().toString() + "\n");
		if(calificacionSearch == null) {
			Curso curso = cursoRep.findById(cursoId).orElse(null);
			Estudiante estudiante = estudianteRep.findById(estudianteId).orElse(null);
			
			calificacion = new Calificacion();			
			calificacion.setCurso(curso);		
			calificacion.setEstudiante(estudiante);
			
			calificacion = repository.save(calificacion);			
		} else { calificacion = calificacionSearch; }		
		return calificacion;
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
