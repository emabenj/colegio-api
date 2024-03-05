package pe.colegio.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.*;
import pe.colegio.repository.EstudianteRep;
import pe.colegio.repository.UsuarioRep;
import pe.colegio.service.*;

@RestController @RequestMapping("/apoderados")
public class ApoderadoController {
	@Autowired
	private ApoderadoServ service;
	@Autowired
	private UsuarioRep usuarioRep;
	@Autowired
	private EstudianteRep estudianteRep;
	
	public ApoderadoController() {}

	// LISTAR APODERADOS
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD')")
	@GetMapping
	public ResponseEntity<Collection<Apoderado>> listarApoderados(
			@RequestParam(name = "estudiante", required = false) Integer estudianteId){
		Collection<Apoderado> apoderados = service.listar(estudianteId);
		return ResponseEntity.ok(apoderados);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD')")
	@GetMapping("/{id}")
	public ResponseEntity<Apoderado> obtenerApoderado(@PathVariable("id") Integer apoderadoId) {
		Apoderado apoderado = service.buscarPorId(apoderadoId);
		return apoderado != null ? ResponseEntity.ok(apoderado) : ResponseEntity.badRequest().build();
	}
	// AGREGAR APODERADO
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> agregarApoderado(@RequestBody Apoderado apoderado) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "";
		try {
			if(usuarioRep.existsByEmail(apoderado.getCorreo()) && service.buscarPorCorreo(apoderado.getCorreo()) != null) {
				msg = "El correo ya se encuentra registrado.";
			}
			else if(!(apoderado.getTelefono()>899999999 && apoderado.getTelefono()<1000000000)) {
				msg = "El formato del número de teléfono no es correcto.";
			}
			if (msg.isEmpty()) {
				Set<Integer> estudiantesIds = apoderado.getItemsEstudiante().stream().map(Estudiante::getEstudianteId)
						.collect(Collectors.toSet());
				msg = estudiantesIds.isEmpty() ? "Faltó establecer un estudiante que sea hijo del apoderado." : "";
				List<Integer> estudiantesNoRegistrados = estudiantesIds.stream().filter(id -> !estudianteRep.existsById(id))
						.collect(Collectors.toList());
				
				if ( msg.isEmpty() && !estudiantesNoRegistrados.isEmpty()) { msg = "Uno o más estudiantes no se encuentran registrados."; }
				if (msg.isEmpty()) { return new ResponseEntity<>(service.agregar(apoderado).getApoderadoId(), HttpStatus.CREATED); }
			}
		} catch (DataIntegrityViolationException e) {
			msg = "Error al agregar el apoderado: El correo o el teléfono ya se encuentra registrado.";
		} catch (Exception e) {
			msg = "Error al agregar el apoderado: "+ e.toString();
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ACTUALIZAR APODERADO
	@PreAuthorize("hasRole('APOD')")
	@PutMapping
	public ResponseEntity<?> actualizarApoderado(@RequestBody Apoderado apoderado) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Apoderado actualizado.";
		try {
			if(usuarioRep.existsByEmail(apoderado.getCorreo()) && service.buscarPorCorreo(apoderado.getCorreo()) != null) {
				service.actualizar(apoderado);
				headers.set("message", msg);
				return ResponseEntity.ok().headers(headers).build();
			} msg = "No se encontró al apoderado.";
		} catch (DataIntegrityViolationException e) { msg = "Error al actualizar: Se ingresó un valor duplicado."; } 
		catch (Exception e) { msg = "Error al actualizar el apoderado: " + e.getMessage(); }
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
}