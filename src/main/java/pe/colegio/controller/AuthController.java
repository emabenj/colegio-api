package pe.colegio.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		String passChange="cambiarContra123";
		String email = usuario.getEmail();
		String pass = usuario.getContrasena();
		
		Usuario prueba = repository.findByEmail(email).orElse(null);
		
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		if(prueba != null && bCrypt.matches(passChange, prueba.getContrasena())) {
			prueba.setContrasena(bCrypt.encode(pass));
			repository.save(prueba);
		}
		
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
	@PutMapping("/contrasena")
	public ResponseEntity<?> actualizarContrasena(@RequestBody Usuario usuario) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Contraseña actualizada.";
		String correo = usuario.getEmail().trim(), contra = usuario.getContrasena().trim();
		if (!correo.isEmpty() && !contra.isEmpty()){
			if(service.buscarPorCorreo(usuario.getEmail()) != null) {
				service.actualizar(usuario);
				headers.set("message", msg);
				System.out.println(msg);
				return ResponseEntity.ok().headers(headers).build();
			}
		} else { msg = "Completar los campos de correo electrónico y contraseña adecuadamente"; }
		headers.set("message", msg);
		System.out.println(msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// COMPROBAR CORREO
	@GetMapping("/email")
	public ResponseEntity<?> comprobarEmail(@RequestParam(required = false, value = "correo")String correo){
		HttpHeaders headers = new HttpHeaders();
		Boolean result = repository.existsByEmail(correo);
		headers.set("message", result.toString());
		return result ? ResponseEntity.ok().headers(headers).build() : ResponseEntity.badRequest().headers(headers).build();
	}
	// OBTENER SESION
	@GetMapping("session")
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