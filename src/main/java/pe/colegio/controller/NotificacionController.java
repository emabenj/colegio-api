package pe.colegio.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Notificacion;
import pe.colegio.repository.UsuarioRep;
import pe.colegio.service.*;

@RestController @RequestMapping("/notificaciones")
public class NotificacionController {
	@Autowired
	private NotificacionServ service;
	@Autowired
	private UsuarioRep usuarioRep;
	
	public NotificacionController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping
	public ResponseEntity<Collection<Notificacion>> listarNotificacions(
			@RequestParam(name = "usuario", required = false) Integer usuarioId){
//		Collection<Notificacion> notificaciones = service.listar(usuarioId);
		Collection<Notificacion> notificaciones = usuarioRep.findById(usuarioId).orElse(null).getNotificaciones();
		return ResponseEntity.ok(notificaciones);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping("/{id}")
	public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		Notificacion notificacion = service.buscarPorId(id);
		if(notificacion != null) { return ResponseEntity.ok(notificacion); }
		headers.set("message", "Notificacion no encontrada.");
		return ResponseEntity.badRequest().build();
	}
	// AGREGAR NOTIFICACION
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@PostMapping
	public ResponseEntity<Notificacion> agregarNotificacion(@RequestBody Notificacion notificacion,
			@RequestParam(name = "tipo", required = false) String tipo) {
		HttpHeaders headers = new HttpHeaders();
		String msg;
		try {
			List<Integer> idsUsuarios = notificacion.getUsuarios().stream().map(u->u.getUsuarioId()).collect(Collectors.toList());
			if(idsUsuarios.isEmpty()) {
				msg = "No se establecieron usuarios para la notificacion.";
			} else if (usuarioRep.existsAllByIds(idsUsuarios, Long.valueOf(idsUsuarios.size()))) {
				msg = "Uno o varios de los usuarios no se encuentran registrados.";
			} else {
				msg = "Los campos de titulo y descripcion deben completarse.";
				if(!notificacion.getTitulo().trim().isEmpty() && !notificacion.getDescripcion().trim().isEmpty()) {
					return new ResponseEntity<Notificacion>(service.agregar(notificacion, tipo), HttpStatus.CREATED);
				}
			}
		}catch(NullPointerException e) {
			msg = "Se ha ingresado un valor nulo.";
			System.out.println(e.getMessage());
		}catch(InvalidDataAccessApiUsageException e) {
			msg = "Se ha ingresado un valor nulo para el id de los usuarios.";
			System.out.println(e.getMessage());
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ACTUALIZAR NOTIFICACION
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<?> actualizarNotificacion(@RequestBody Notificacion notificacion) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Notificacion actualizada.";
		try {
			List<Integer> idsUsuarios = notificacion.getUsuarios().stream().map(u->u.getUsuarioId()).collect(Collectors.toList());
			if(idsUsuarios.isEmpty()) {
				msg = "No se encontraron usuarios en la notificacion.";
			} else if (!usuarioRep.existsAllByIds(idsUsuarios, Long.valueOf(idsUsuarios.size()))) {
				msg = "Uno o varios de los usuarios no se encuentran registrados.";
			} else if(service.buscarPorId(notificacion.getNotificacionId()) == null) {
				msg = "La notificacion no se encuentra registrada.";
			} else {
				if(!notificacion.getDescripcion().trim().isEmpty() && !notificacion.getTitulo().trim().isEmpty()) {
					service.actualizar(notificacion);
					headers.set("message", msg);
					return ResponseEntity.ok().headers(headers).build();
				}
				msg = "Los campos de titulo y descripcion deben completarse.";			
			}
		}catch(InvalidDataAccessApiUsageException e) {
			msg = "Se ha ingresado un valor nulo para el id del administrador o la notificacion";
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ELIMINAR NOTIFICACION
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarNotificacion(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		if(service.buscarPorId(id) != null) {
			service.eliminar(id);
			headers.set("message", "Notificacion eliminada.");
			return ResponseEntity.ok().headers(headers).build();
		}
		headers.set("message", "La notificacion no se encuentra registrada.");
		return ResponseEntity.badRequest().headers(headers).build();
	}
}