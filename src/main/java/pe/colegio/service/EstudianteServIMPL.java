package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Estudiante;
import pe.colegio.repository.EstudianteRep;

@Service
public class EstudianteServIMPL implements EstudianteServ{	
	@Autowired
	private EstudianteRep repository;
	
	public EstudianteServIMPL() {}

	//LISTAR ESTUDIANTES
	@Override @Transactional(readOnly=true)
	public Collection<Estudiante> listar(Integer cursoId){
		Collection<Estudiante> estudiantes = cursoId != null ? repository.findByItemsCurso_CursoId(cursoId) : repository.findAll();
		return estudiantes;
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