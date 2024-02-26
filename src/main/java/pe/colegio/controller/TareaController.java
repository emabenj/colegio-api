package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Tarea;
import pe.colegio.service.*;

@RestController @RequestMapping("/tareas")
public class TareaController {
	@Autowired
	private TareaServ service;
	
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
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Tarea> agregarTarea(@RequestBody Tarea tarea) {
		tarea.setTareaId(0);
		service.agregar(tarea);
		return new ResponseEntity<Tarea>(tarea, HttpStatus.CREATED);
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