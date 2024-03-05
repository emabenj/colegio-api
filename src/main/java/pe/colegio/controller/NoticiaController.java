package pe.colegio.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Noticia;
import pe.colegio.repository.AdministradorRep;
import pe.colegio.service.*;

@RestController @RequestMapping("/noticias")
public class NoticiaController {
	@Autowired
	private NoticiaServ service;
	@Autowired
	private AdministradorRep adminRep;
	
	public NoticiaController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping
	public ResponseEntity<Collection<Noticia>> listarNoticias(){
		Collection<Noticia> noticias = service.listar();
		return ResponseEntity.ok(noticias);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping("/{id}")
	public ResponseEntity<Noticia> obtenerNoticia(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		Noticia noticia = service.buscarPorId(id);
		if(noticia != null) { return ResponseEntity.ok(noticia); }
		headers.set("message", "Noticia no encontrada.");
		return ResponseEntity.badRequest().build();
	}
	// AGREGAR NOTICIA
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Noticia> agregarNoticia(@RequestBody Noticia noticia) {
		HttpHeaders headers = new HttpHeaders();
		String msg;
		try {
			if (adminRep.findById(noticia.getAdministrador().getAdministradorId()).orElse(null) == null) {
				msg = "El administrador no está registrado.";
			}else {
				msg = "Completar los campos de título y contenido adecuadamente.";
				if(!noticia.getContenido().trim().isEmpty() && !noticia.getTitulo().trim().isEmpty()) {					;
					return new ResponseEntity<Noticia>(service.agregar(noticia), HttpStatus.CREATED);
				}
			}
		}catch(NullPointerException e) {
			msg = "Se ingresó un valor nulo.";
		}catch(InvalidDataAccessApiUsageException e) {
			msg = "Se ingresó un valor nulo para el id del administrador.";
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ACTUALIZAR NOTICIA
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<?> actualizarNoticia(@RequestBody Noticia noticia) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Noticia actualizada.";
		try {
			if (adminRep.findById(noticia.getAdministrador().getAdministradorId()).orElse(null) == null) {
				msg = "El administrador no está registrado.";
			} else if(service.buscarPorId(noticia.getNoticiaId()) == null) {
				msg = "La noticia no está registrada.";
			} else {
				if(!noticia.getContenido().trim().isEmpty() && !noticia.getTitulo().trim().isEmpty()) {
					service.actualizar(noticia);
					headers.set("message", msg);
					return ResponseEntity.ok().headers(headers).build();
				}
				msg = "Completar los campos de título, contenido e imagen adecuadamente.";			
			}
		}catch(InvalidDataAccessApiUsageException e) {
			msg = "Se ingresó un valor nulo para el id del administrador o la noticia";
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ELIMINAR NOTICIA
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarNoticia(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		if(service.buscarPorId(id) != null) {
			service.eliminar(id);
			headers.set("message", "Noticia eliminada.");
			return ResponseEntity.ok().headers(headers).build();
		}
		headers.set("message", "La noticia no está registrada.");
		return ResponseEntity.badRequest().headers(headers).build();
	}
}