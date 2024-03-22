package pe.colegio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Calificacion;
import pe.colegio.repository.AdministradorRep;
import pe.colegio.repository.CursoRep;
import pe.colegio.repository.EstudianteRep;
import pe.colegio.service.CalificacionServ;

@RestController @RequestMapping("/calificaciones")
public class CalificacionController {
	@Autowired
	private CalificacionServ service;
	@Autowired
	private CursoRep cursoRep;
	@Autowired
	private EstudianteRep estudianteRep;
	
	public CalificacionController() {}
	
	// BUSCAR POR ALUMNOID Y CURSOID
	@PreAuthorize("hasRole('APOD') or hasRole('DOC') or hasRole('APOD')")
	@PostMapping
	public ResponseEntity<Calificacion> obtenerCalificacionPorEstudianteYCurso(
			@RequestParam("estudiante") Integer estudianteId,
			@RequestParam("curso") Integer cursoId) {
//		HttpHeaders headers = new HttpHeaders();
    	Calificacion nota = service.buscarPorEstudianteYCurso(estudianteId, cursoId);
    	return ResponseEntity.ok(nota);
//		if(nota != null) {  }
//		headers.set("message", "Calificaciones no encontradas.");
//		return ResponseEntity.badRequest().build();
	}
	@PreAuthorize("hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarCalificacion(@RequestBody Calificacion calificacion) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "No se pudo actualizar.";
		try {
			if (cursoRep.findById(calificacion.getCurso().getCursoId()).orElse(null) == null) {
				msg = "El curso no se encuentra registrado.";
			} else if(service.buscarPorId(calificacion.getEstudiante().getEstudianteId()) == null) {
				msg = "El estudiante no se encuentra registrado.";
			} else {
				service.actualizar(calificacion);
				msg = "Calificaciones actualizadas.";
				headers.set("message", msg);
				return ResponseEntity.ok().headers(headers).build();		
			}
		}catch(InvalidDataAccessApiUsageException e) {
			msg = "Se ingresó un valor nulo para el id del curso o el estudiante";
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
}