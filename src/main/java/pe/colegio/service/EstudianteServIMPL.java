package pe.colegio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Apoderado;
import pe.colegio.entity.Aula;
import pe.colegio.entity.Estudiante;
import pe.colegio.repository.EstudianteRep;

@Service
public class EstudianteServIMPL implements EstudianteServ{	
	@Autowired
	private EstudianteRep repository;
	
	public EstudianteServIMPL() {}

	//LISTAR ESTUDIANTES
	@Override @Transactional(readOnly=true)
	public Collection<Estudiante> listar(Integer cursoId, String nivel, Integer grado, String seccion){
		Character niv;
		Character sec;
		Collection<Estudiante> estudiantes = new ArrayList();
		if (cursoId != null && grado != null && seccion != null) {
			sec = seccion.toCharArray()[0];
			estudiantes = repository.findByItemsCurso_CursoIdAndGradoAndSeccion(cursoId, grado, sec);
		}
		else if(nivel != null && grado != null && seccion != null) {
			niv = nivel.toCharArray()[0];
			sec = seccion.toCharArray()[0];
			estudiantes = repository.findByNivelEducativoAndGradoAndSeccion(niv, grado, sec);
		}
		else if (cursoId != null && grado != null) { estudiantes = repository.findByItemsCurso_CursoIdAndGrado(cursoId, grado); }
		else {
			estudiantes = cursoId != null ? repository.findByItemsCurso_CursoId(cursoId) : repository.findByItemsApoderadoSizeLessThan(3);
		}
		return estudiantes;
	}
	private void insertAulas(Integer curso, Integer gr, Character sc, Collection<Aula> aulas) {
		Integer maxAulas = 3;
		Collection<Estudiante> estudiantes = repository.findByItemsCurso_CursoIdAndGradoAndSeccion(curso, gr, sc);		
		estudiantes.stream().map(e->e.getItemsApoderado().parallelStream().map(a->a).collect(Collectors.toList()));
		if(!estudiantes.isEmpty() && aulas.size() < maxAulas) {
//			Collection<Apoderado> apoderados = new ArrayList();
			Collection<String> emailsApoderados = new ArrayList();
			for(Estudiante e: estudiantes) {
				if(e.getItemsApoderado().size()>0) {
//					apoderados.addAll(e.getItemsApoderado().stream().map(a->a).collect(Collectors.toList())) ;
					emailsApoderados.addAll(e.getItemsApoderado().stream().map(a->a.getCorreo()).collect(Collectors.toList())) ;	
				}
			}
			Aula aula = new Aula(estudiantes, gr.toString(), sc.toString(), emailsApoderados);
			aulas.add(aula);
		}
	}
		
	//LISTAR ESTUDIANTES POR AULA 
	@Override @Transactional(readOnly=true)
	public Collection<Aula> obtenerAulas(Integer cursoId){
		Collection<Aula> aulas = new ArrayList();
		Collection<Integer> grados = IntStream.range(1, 7).boxed().collect(Collectors.toList());
		Collection<Character> secciones = Arrays.asList('A','B', 'C');
		if (cursoId != null) {
			grados.stream().forEach(gr -> secciones.stream().forEach(sc -> { insertAulas(cursoId, gr, sc, aulas); }));			
		}		
		return aulas;
	}
	//BUSCAR ESTUDIANTE
	@Override @Transactional(readOnly = true)
	public Estudiante buscarPorId(Integer id, Integer dni) {
		Estudiante estudiante = null;
		estudiante = dni == null ? repository.findById(id).orElse(estudiante) : repository.findByDni(dni); 
		return estudiante;
	}
	//AGREGAR ESTUDIANTE
//	@Override @Transactional
//	public void agregar(Estudiante estudiante) {
//		estudiante.setEstudianteId(null);
//		repository.save(estudiante);
//	}
	//ACTUALIZAR ESTUDIANTE
	@Override @Transactional
	public void actualizar(Estudiante estudiante) {
		repository.save(estudiante);		
	}
	//ELIMINAR ESTUDIANTE
//	@Override @Transactional
//	public void eliminar(Integer id) {
//		//CAMBIAR ESTADO
//		Estudiante estudiante = buscarPorId(id);
//		estudiante.setEstado(EstadoType.DELETED.name());
//		repository.save(estudiante);
//	}
}