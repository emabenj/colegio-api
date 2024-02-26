package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Evento;
import pe.colegio.service.*;

@RestController @RequestMapping("/eventos")
public class EventoController {
	@Autowired
	private EventoServ service;
	
	public EventoController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Evento>> listarEventos(){
		Collection<Evento> eventos = new ArrayList<>();
		eventos = service.listar();
		return ResponseEntity.ok(eventos);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Evento> obtenerEvento(@PathVariable("id") Integer id) {
		Evento evento = service.buscarPorId(id);
		if(evento != null) { return ResponseEntity.ok(evento); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR EVENTO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Evento> agregarEvento(@RequestBody Evento evento) {
		evento.setEventoId(0);
		service.agregar(evento);
		return new ResponseEntity<Evento>(evento, HttpStatus.CREATED);
	}
	// ACTUALIZAR EVENTO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarEvento(@RequestBody Evento evento) {
		if(service.buscarPorId(evento.getEventoId()) != null) {
			service.actualizar(evento);
			return ResponseEntity.ok("Evento actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR EVENTO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarEvento(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Este evento no puede ser eliminado.";
			service.eliminar(id);
			return ResponseEntity.ok("Evento eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}