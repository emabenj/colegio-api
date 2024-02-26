package pe.colegio.controller;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pe.colegio.entity.Administrador;
import pe.colegio.entity.Usuario;
import pe.colegio.service.*;

@RestController @RequestMapping("/administradores")
public class AdministradorController {
	@Autowired
	private UsuarioServ service;
	
	public AdministradorController() {}
//
//	// LISTAR
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
//	@GetMapping
//	public ResponseEntity<Collection<Administrador>> listarAdministradors(
//			@RequestParam(required = false, value = "alumnoId") Integer id,
//			@RequestParam(required = false, value = "ne") String nivelEducativo,
//			@RequestParam(required = false, value = "tn") String turno,
//			@RequestParam(required = false, value = "dia") String dia){
//		Collection<Administrador> administradores = new ArrayList<>();
//		if (id != null) {
//			if(alumnoService.buscarPorId(id) == null) {
//				HttpHeaders headers = new HttpHeaders();
//				headers.set("message", "Alumno no encontrado.");
//				return ResponseEntity.notFound().headers(headers).build();
//			}
//			administradores = service.listarPorAlumnoId(id);
//		}else{
//			administradores = service.listar(nivelEducativo, turno, dia);
//		}
//		return ResponseEntity.ok(administradores);
//	}
//	// BUSCAR POR ID
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
//	@GetMapping("/{id}")
//	public ResponseEntity<Administrador> obtenerAdministrador(@PathVariable("id") Integer id) {
//		Administrador administrador = service.buscarPorId(id);
//		if(administrador != null) { return ResponseEntity.ok(administrador); }
//		return ResponseEntity.notFound().build();
//	}
//	// AGREGAR ADMINISTRADOR
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
//	@PostMapping
//	public ResponseEntity<Administrador> agregarAdministrador(@RequestBody Administrador administrador) {
//		administrador.setAdministradorId(0);
//		service.agregar(administrador);
//		return new ResponseEntity<Administrador>(administrador, HttpStatus.CREATED);
//	}
	// ACTUALIZAR ADMINISTRADOR
//	// ELIMINAR ADMINISTRADOR
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> eliminarAdministrador(@PathVariable("id") Integer id) {
//		if(service.buscarPorId(id) != null) {
//			String msg = "Este administrador no puede ser eliminado.";
//			service.eliminar(id);
//			return ResponseEntity.ok("Administrador eliminado");
//		}
//		return ResponseEntity.notFound().build();
//	}
}