package pe.colegio.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Asistencia;
import pe.colegio.repository.AsistenciaRep;

@Service
public class AsistenciaServIMPL implements AsistenciaServ{
	@Autowired
	private AsistenciaRep repository;

	// BUSCAR
	@Override @Transactional(readOnly = true)
	public Asistencia buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
//
//	// LISTAR ASISTENCIAS by alumnoId
//	@Override @Transactional(readOnly = true)
//	public Collection<Asistencia> listar(Integer alumnoId) {
//		return repository.findAll().stream()
//                .filter(asistencia -> asistencia.getItemsAlumno().stream()
//                .anyMatch(alumno -> alumno.getAlumnoId().equals(alumnoId)))
//                .collect(Collectors.toList());
//	}
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
//
//	// ELIMINAR CONJUNTO DE ASISTENCIAS
//	@Override @Transactional 
//	public void eliminar(Integer id) {
//		repository.deleteById(id);
//	}	
}