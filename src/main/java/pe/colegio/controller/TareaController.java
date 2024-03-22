package pe.colegio.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pe.colegio.entity.Curso;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Estudiante;
import pe.colegio.entity.Tarea;
import pe.colegio.repository.CursoRep;
import pe.colegio.service.*;

@RestController @RequestMapping("/tareas")
public class TareaController {
	@Autowired
	private TareaServ service;
	@Autowired
	private CursoRep cursoRep;
	
	public TareaController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Tarea>> listarTareas(){
		Collection<Tarea> tareas = new ArrayList<>();
		tareas = service.listar();
		return ResponseEntity.ok(tareas);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Tarea> obtenerTarea(@PathVariable("id") Integer id) {
		Tarea tarea = service.buscarPorId(id);
		if(tarea != null) { return ResponseEntity.ok(tarea); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR TAREA
	@PreAuthorize("hasRole('DOC')")
	@PostMapping
	public ResponseEntity<?> agregarTarea(@RequestBody Tarea tarea) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "";
		try {			
			if (msg.isEmpty()) {
				Set<Integer> cursosIds = tarea.getItemsCurso().stream().map(Curso::getCursoId)
						.collect(Collectors.toSet());
//				Set<Integer> estudiantesIds = estudiantes.stream().map(Estudiante::getEstudianteId)
//						.collect(Collectors.toSet());
				msg = cursosIds.isEmpty() ? "Falta establecer un curso a la tarea." : "";
				List<Integer> cursosNoRegistrados = cursosIds.stream().filter(id -> !cursoRep.existsById(id))
						.collect(Collectors.toList());
				
				if (msg.isEmpty() && !cursosNoRegistrados.isEmpty()) { msg = "Uno o más cursos no se encuentran registrados."; }
				if (msg.isEmpty()) {
					service.agregar(tarea);
					return new ResponseEntity<>(HttpStatus.CREATED); }
			}
		} catch (DataIntegrityViolationException e) {
			msg = "Hay un curso repetido.";
		} catch (Exception e) {
			msg = "Error al agregar: "+ e.toString();
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ACTUALIZAR TAREA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarTarea(@RequestBody Tarea tarea) {
		if(service.buscarPorId(tarea.getTareaId()) != null) {
			service.actualizar(tarea);
			return ResponseEntity.ok("Tarea actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR TAREA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarTarea(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Esta tarea no puede ser eliminada.";
			service.eliminar(id);
			return ResponseEntity.ok("Tarea eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}