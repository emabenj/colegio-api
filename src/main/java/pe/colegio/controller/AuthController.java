package pe.colegio.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.*;
import pe.colegio.repository.*;
import pe.colegio.security.JwtAuthResponse;
import pe.colegio.security.JwtTokenProvider;
import pe.colegio.service.UsuarioServ;
import pe.colegio.util.EstadoType;

@RestController @RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRep repository;
	
	@Autowired
	private Rol_UsuarioRep rolRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UsuarioServ service;
	
	public AuthController() {}

	// AUTHENTICACION
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> iniciarSesion(@RequestBody Usuario usuario,
			@RequestParam("rol") String rol) {
		String email = usuario.getEmail();
		String pass = usuario.getContrasena();
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pass));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Token del jwtTokenProvider
		String token = jwtTokenProvider.generarToken(authentication);
		if(repository.findByEmail(email).orElse(null).tieneRol(rol)) {
			return ResponseEntity.ok(new JwtAuthResponse(token));
		}
		return ResponseEntity.notFound().build();		
	}
	// BUSCAR POR ID, USUARIO
	@GetMapping("/buscar")
	public ResponseEntity<?> obtenerUsuario(
			@RequestParam(required = false, value = "id")Integer id,
			@RequestParam(required = false, value = "correo")String correo) {

		Usuario usuario = null;
		if (id != null) { usuario = service.buscarPorId(id); }
		else if(correo != null) { usuario = service.buscarPorCorreo(correo); }
		
		if(usuario != null) { return ResponseEntity.ok(usuario); }
		return ResponseEntity.notFound().build();
	}
	// ACTUALIZAR CONTRASEÑA
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@PutMapping("/contrasena")
	public ResponseEntity<?> actualizarContrasena(@RequestBody Usuario usuario) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Contraseña actualizada.";
		String correo = usuario.getEmail().trim(), contra = usuario.getContrasena().trim();
		if (!correo.trim().isEmpty() && !contra.isEmpty()){
			if(service.buscarPorCorreo(usuario.getEmail()) != null) {
				if (!contra.isEmpty()) {
					service.actualizar(usuario);
					return ResponseEntity.ok().headers(headers).build();
				} msg = "Completar el campo de la contraseña.";
			}
		} else { msg = "Completar los campos de correo electrónico y contraseña adecuadamente"; }
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// COMPROBAR CORREO
	@GetMapping
	public ResponseEntity<?> comprobarEmail(@RequestParam(required = false, value = "correo")String correo){
		HttpHeaders headers = new HttpHeaders();
		Boolean result = repository.existsByEmail(correo);
		headers.set("message", result.toString());
		return result ? ResponseEntity.ok().headers(headers).build() : ResponseEntity.badRequest().headers(headers).build();
	}
	// OBTENER SESION
	@GetMapping
	public ResponseEntity<?> obtenerSesion(HttpServletRequest request, @RequestParam("rol") String rol) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null) {
			bearerToken = bearerToken.substring(7,bearerToken.length());
			String email = jwtTokenProvider.obtenerUsernameDelJWT(bearerToken);
			Usuario usuario = repository.findByEmail(email).orElse(null);
			if(usuario != null && usuario.tieneRol(rol)) { return ResponseEntity.ok(usuario); }
		}
		return ResponseEntity.notFound().build();
	}
	// RESET ESTADOS
	@PutMapping("/desactivar")
	public ResponseEntity<?> actualizarEstados(){
		service.cambiarEstadoUsuarios(EstadoType.INACTIVE.name());
		String c = String.valueOf(repository.findAll().size());
		
		return new ResponseEntity<>("Usuarios desactivados exitosamente: "+c, HttpStatus.OK);
	}
}