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
		if(apoderado != null) { return ResponseEntity.ok(apoderado); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR APODERADO
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> agregarApoderado(@RequestBody Apoderado apoderado) {
		try {
			String msg = "";
			if(usuarioRep.existsByEmail(apoderado.getCorreo()) && service.buscarPorCorreo(apoderado.getCorreo()) != null) {
				return new ResponseEntity<>("El correo ya se encuentra registrado.", HttpStatus.BAD_REQUEST);
			}
			else if(!(apoderado.getTelefono()>899999999 && apoderado.getTelefono()<1000000000)) {
				return new ResponseEntity<>("El formato del número de teléfono no es correcto.", HttpStatus.BAD_REQUEST);
			}
			Set<Integer> estudiantesIds = apoderado.getItemsEstudiante().stream().map(Estudiante::getEstudianteId)
					.collect(Collectors.toSet());
			msg = estudiantesIds.isEmpty() ? "Faltó establecer un estudiante que sea hijo del apoderado." : "";
			List<Integer> estudiantesNoRegistrados = estudiantesIds.stream().filter(id -> !estudianteRep.existsById(id))
					.collect(Collectors.toList());
			
			if ( msg == "" && !estudiantesNoRegistrados.isEmpty()) { msg = "Uno o más estudiantes no se encuentran registrados."; }
			if (msg != "") { return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST); }
			apoderado = service.agregar(apoderado);
			return new ResponseEntity<Apoderado>(apoderado, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
		    return new ResponseEntity<>("Error al agregar el apoderado: El correo o el teléfono ya se encuentra registrado.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		    return new ResponseEntity<>("Error al agregar el apoderado: " + e.toString(), HttpStatus.BAD_REQUEST);
		}
	}
	// ACTUALIZAR APODERADO
	@PreAuthorize("hasRole('APOD')")
	@PutMapping
	public ResponseEntity<?> actualizarApoderado(@RequestBody Apoderado apoderado) {
		try {
			if(usuarioRep.existsByEmail(apoderado.getCorreo()) && service.buscarPorCorreo(apoderado.getCorreo()) != null) {
				service.actualizar(apoderado);
				return ResponseEntity.ok("Apoderado actualizado.");
			}
			return ResponseEntity.notFound().build();
		} catch (DataIntegrityViolationException e) {
		    return new ResponseEntity<>("Error al actualizar: Se ingresó un valor duplicado.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
		    return new ResponseEntity<>("Error al agregar el apoderado: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
}