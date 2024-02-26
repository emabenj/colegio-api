package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Noticia;
import pe.colegio.service.*;

@RestController @RequestMapping("/noticias")
public class NoticiaController {
	@Autowired
	private NoticiaServ service;
	
	public NoticiaController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Noticia>> listarNoticias(){
		Collection<Noticia> noticias = new ArrayList<>();
		noticias = service.listar();
		return ResponseEntity.ok(noticias);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Noticia> obtenerNoticia(@PathVariable("id") Integer id) {
		Noticia noticia = service.buscarPorId(id);
		if(noticia != null) { return ResponseEntity.ok(noticia); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR NOTICIA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Noticia> agregarNoticia(@RequestBody Noticia noticia) {
		noticia.setNoticiaId(0);
		service.agregar(noticia);
		return new ResponseEntity<Noticia>(noticia, HttpStatus.CREATED);
	}
	// ACTUALIZAR NOTICIA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarNoticia(@RequestBody Noticia noticia) {
		if(service.buscarPorId(noticia.getNoticiaId()) != null) {
			service.actualizar(noticia);
			return ResponseEntity.ok("Noticia actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
	// ELIMINAR NOTICIA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarNoticia(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Esta noticia no puede ser eliminada.";
			service.eliminar(id);
			return ResponseEntity.ok("Noticia eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}