package pe.colegio.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Curso;
import pe.colegio.service.*;

@RestController @RequestMapping("/cursos")
public class CursoController {
	@Autowired
	private CursoServ service;
	
	public CursoController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping
	public ResponseEntity<Collection<Curso>> listarCursos(
			@RequestParam(required = false, value = "estudiante") Integer estudianteId){
		Collection<Curso> cursos = service.listar(estudianteId);
		return ResponseEntity.ok(cursos);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping("/{id}")
	public ResponseEntity<Curso> obtenerCurso(@PathVariable("id") Integer id) {
		Curso curso = service.buscarPorId(id);
		if(curso != null) { return ResponseEntity.ok(curso); }
		return ResponseEntity.notFound().build();
	}
//	// AGREGAR CURSO
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
//	@PostMapping
//	public ResponseEntity<Curso> agregarCurso(@RequestBody Curso curso) {
//		curso.setCursoId(0);
//		service.agregar(curso);
//		return new ResponseEntity<Curso>(curso, HttpStatus.CREATED);
//	}
//	// ACTUALIZAR CURSO
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
//	@PutMapping
//	public ResponseEntity<?> actualizarCurso(@RequestBody Curso curso) {
//		if(service.buscarPorId(curso.getCursoId()) != null) {
//			service.actualizar(curso);
//			return ResponseEntity.ok("Curso actualizado.");
//		}
//		return ResponseEntity.notFound().build();
//	}
//	// ELIMINAR CURSO
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> eliminarCurso(@PathVariable("id") Integer id) {
//		if(service.buscarPorId(id) != null) {
//			String msg = "Este curso no puede ser eliminado.";
//			service.eliminar(id);
//			return ResponseEntity.ok("Curso eliminado");
//		}
//		return ResponseEntity.notFound().build();
//	}
}