package pe.colegio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Estudiante;
import pe.colegio.repository.AsistenciaRep;
import pe.colegio.repository.EstudianteRep;

@Service
public class AsistenciaServIMPL implements AsistenciaServ{
	@Autowired
	private AsistenciaRep repository;
	@Autowired
	private EstudianteRep estudianteRep;

	// BUSCAR
	@Override @Transactional(readOnly = true)
	public Asistencia buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// BUSCAR
	@Override @Transactional(readOnly = true)
	public Asistencia buscarPorIdYEstudiante(Integer estudianteId, LocalDate fecha) {
		return repository.findByFechaAndItemsEstudiante_EstudianteId(estudianteId, fecha);
	}

	// LISTAR ASISTENCIAS
	@Override @Transactional
	public Collection<Asistencia> listar(LocalDate fecha, Collection<Estudiante> estudiantes) {
		Collection<Asistencia> asistencias = new ArrayList();
		Collection<Asistencia> asistenciasSearch = repository.findByFechaAndEstudiantes(fecha, estudiantes);
		
		if(asistenciasSearch.isEmpty()) {
			for(Estudiante est : estudiantes) {
				if(estudianteRep.existsById(est.getEstudianteId())) {
					Asistencia asistencia = new Asistencia(fecha);
					asistencia.setItemsEstudiante(Collections.singleton(est));
					asistencias.add(repository.save(asistencia));
				}
			}
		}else {
			asistencias = asistenciasSearch;
		}		
		return asistencias;
	}
//
//	// LISTAR ASISTENCIAS by FechaInicial
//	@Override @Transactional(readOnly = true)
//	public Collection<Asistencia> listaByFI(LocalDate fi) {
//		return repository.findAll().stream()
//	            .filter(asistencia -> asistencia.getFecha().isAfter(fi.plusDays(-1)) && asistencia.getFecha().isBefore(fi.plusDays(5)))
//	            .collect(Collectors.toList());
//	}
//	// AGREGAR CONJUNTO DE ASISTENCIAS
//	@Override @Transactional
//	public void agregar(Asistencia asistencia) {
//		repository.save(asistencia);
//	}
//
//	// ACTUALIZAR CONJUNTO DE ASISTENCIAS
//	@Override @Transactional
//	public void actualizar(Asistencia asistencia) {
//		repository.save(asistencia);		
//	}
	// ACTUALIZAR CONJUNTO DE ASISTENCIAS
	@Override @Transactional
	public Collection<Asistencia> actualizarAsistencias(Collection<Asistencia> asistencias) {
		Collection<Asistencia> asistenciasEvaluadas = new ArrayList();
		for (Asistencia as: asistencias) {
			Asistencia asistencia = buscarPorId(as.getAsistenciaId());
			if(asistencia != null) {
				asistencia.setEstado(as.getEstado());
				asistenciasEvaluadas.add(repository.save(asistencia));				
			}
		}
		return asistenciasEvaluadas;
		
	}
//
//	// ELIMINAR CONJUNTO DE ASISTENCIAS
//	@Override @Transactional 
//	public void eliminar(Integer id) {
//		repository.deleteById(id);
//	}	
}