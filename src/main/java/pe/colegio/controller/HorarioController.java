package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Horario;
import pe.colegio.service.*;

@RestController @RequestMapping("/horarios")
public class HorarioController {
	@Autowired
	private HorarioServ service;
	
	public HorarioController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Horario>> listarHorarios(){
		Collection<Horario> horarios = new ArrayList<>();
		horarios = service.listar();
		return ResponseEntity.ok(horarios);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Horario> obtenerHorario(@PathVariable("id") Integer id) {
		Horario horario = service.buscarPorId(id);
		if(horario != null) { return ResponseEntity.ok(horario); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR HORARIO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Horario> agregarHorario(@RequestBody Horario horario) {
		horario.setHorarioId(0);
		service.agregar(horario);
		return new ResponseEntity<Horario>(horario, HttpStatus.CREATED);
	}
	// ACTUALIZAR HORARIO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarHorario(@RequestBody Horario horario) {
		if(service.buscarPorId(horario.getHorarioId()) != null) {
			service.actualizar(horario);
			return ResponseEntity.ok("Horario actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR HORARIO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarHorario(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Este horario no puede ser eliminado.";
			service.eliminar(id);
			return ResponseEntity.ok("Horario eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}