package pe.colegio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pe.colegio.entity.Rol_Usuario;
import pe.colegio.service.Rol_UsuarioServ;

@RestController @RequestMapping("/roles")
public class RolUsuarioController {
	@Autowired
	private Rol_UsuarioServ service;
	
	// BUSCAR POR ID, USUARIO
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerRolDeUsuario(@PathVariable("id") Integer id) {
		Rol_Usuario rol = service.buscarPorId(id);
		if(rol != null) { return ResponseEntity.ok(rol); }
		return ResponseEntity.notFound().build();
	}

}
