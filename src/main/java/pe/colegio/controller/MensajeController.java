package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Mensaje;
import pe.colegio.service.*;

@RestController @RequestMapping("/mensajes")
public class MensajeController {
	@Autowired
	private MensajeServ service;
	
	public MensajeController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Mensaje>> listarMensajes(){
		Collection<Mensaje> mensajes = new ArrayList<>();
		mensajes = service.listar();
		return ResponseEntity.ok(mensajes);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Mensaje> obtenerMensaje(@PathVariable("id") Integer id) {
		Mensaje mensaje = service.buscarPorId(id);
		if(mensaje != null) { return ResponseEntity.ok(mensaje); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR MENSAJE
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Mensaje> agregarMensaje(@RequestBody Mensaje mensaje) {
		mensaje.setMensajeId(0);
		service.agregar(mensaje);
		return new ResponseEntity<Mensaje>(mensaje, HttpStatus.CREATED);
	}
	// ACTUALIZAR MENSAJE
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarMensaje(@RequestBody Mensaje mensaje) {
		if(service.buscarPorId(mensaje.getMensajeId()) != null) {
			service.actualizar(mensaje);
			return ResponseEntity.ok("Mensaje actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR MENSAJE
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarMensaje(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Este mensaje no puede ser eliminado.";
			service.eliminar(id);
			return ResponseEntity.ok("Mensaje eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}