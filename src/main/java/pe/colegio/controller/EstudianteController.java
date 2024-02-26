package pe.colegio.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.*;
import pe.colegio.service.*;

@RestController @RequestMapping("/estudiantes")
public class EstudianteController {
	@Autowired
	private EstudianteServ service;
	@Autowired
	private CursoServ cursoService;
	
	// LISTAR
	@GetMapping
	public ResponseEntity<Collection<Estudiante>> listarEstudiantes(
			@RequestParam(required = false, value = "curso") Integer cursoId){
		Collection<Estudiante> estudiantes = new ArrayList<>();
		if (cursoId != null && cursoService.buscarPorId(cursoId) == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("message", "Curso no encontrado.");
			return ResponseEntity.notFound().headers(headers).build();
		}
		estudiantes = service.listar(cursoId);
		return ResponseEntity.ok(estudiantes);
	}
	// BUSCAR POR ID
	@GetMapping("/{id}")
	public ResponseEntity<Estudiante> obtenerEstudiante(
			@PathVariable("id") Integer id,
			@RequestParam(required = false, value = "dni") Integer dni) {
		Estudiante estudiante = service.buscarPorId(id, dni);
		return estudiante != null ?  ResponseEntity.ok(estudiante) : ResponseEntity.notFound().build();
	}
	// AGREGAR ESTUDIANTE
//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping
//	public ResponseEntity<Estudiante> agregarEstudiante(@RequestBody Estudiante estudiante) {
//		service.agregar(estudiante);
//		return new ResponseEntity<Estudiante>(estudiante, HttpStatus.CREATED);
//	}
	// ACTUALIZAR ESTUDIANTE
	@PreAuthorize("hasRole('APOD') or hasRole('ADMIN')")	
	@PutMapping
	public ResponseEntity<?> actualizarEstudiante(@RequestBody Estudiante estudiante) {
		try {
			if(!(estudiante.getDni()>60000000 && estudiante.getDni()<79999999)) {
				return new ResponseEntity<>("El formato del dni no es correcto.", HttpStatus.BAD_REQUEST);
			}
			if(service.buscarPorId(estudiante.getEstudianteId(), null) != null) {
				service.actualizar(estudiante);
				return ResponseEntity.ok("Estudiante actualizado.");
			}
		} catch (DataIntegrityViolationException e) {
		    return new ResponseEntity<>("Error al actualizar: El número de DNI ya se encuentra registrado.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		    return new ResponseEntity<>("Error al actualizar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR ESTUDIANTE
//	@PreAuthorize("hasRole('ADMIN')")
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> eliminarEstudiante(@PathVariable("id") Integer id) {
//		Estudiante estudiante = service.buscarPorId(id); 
//		if(estudiante != null) {
//			if(!estudiante.getEstado().equals(EstadoType.DELETED.name())) {
//				service.eliminar(id);
//				return ResponseEntity.ok("Estudiante eliminado.");				
//			}else {
//				return ResponseEntity.ok("El estudiante ya se encuentra eliminado.");
//			}
//		}	
//		return ResponseEntity.notFound().build();
//	}
}
