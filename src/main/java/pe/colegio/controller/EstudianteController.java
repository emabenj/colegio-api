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
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Estudiante>> listarEstudiantes(
			@RequestParam(required = false, value = "curso") Integer cursoId,
			@RequestParam(required = false, value = "grado") Integer grado,
			@RequestParam(required = false, value = "seccion") String seccion){
		Collection<Estudiante> estudiantes = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		if (cursoId != null && cursoService.buscarPorId(cursoId) == null) {
			headers.set("message", "Curso no encontrado.");
			return ResponseEntity.badRequest().headers(headers).build();
		}
		estudiantes = service.listar(cursoId, grado, seccion);
		headers.set("message", String.valueOf(estudiantes.size()));
		return ResponseEntity.ok().headers(headers).body(estudiantes);
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
		HttpHeaders headers = new HttpHeaders();
		String msg = "Estudiante actualizado.";
		try {
			if(!(estudiante.getDni()>60000000 && estudiante.getDni()<79999999)) { msg = "El formato del dni no es correcto."; }
			if(service.buscarPorId(estudiante.getEstudianteId(), null) == null) { msg = "El estudiante no está registrado."; }
			else {
				service.actualizar(estudiante);
				headers.set("message", msg);
				return ResponseEntity.ok().headers(headers).build();
			}
		} catch (DataIntegrityViolationException e) { msg = "Error al actualizar: El número de DNI ya se encuentra registrado."; } 
		catch (Exception e) { msg = "Error al actualizar: " + e.getMessage(); }
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
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
