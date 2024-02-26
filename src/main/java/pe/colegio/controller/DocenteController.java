package pe.colegio.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.*;
import pe.colegio.repository.CursoRep;
import pe.colegio.repository.UsuarioRep;
import pe.colegio.service.*;

@RestController @RequestMapping("/docentes")
public class DocenteController {
	@Autowired
	private DocenteServ service;
	@Autowired
	private UsuarioRep usuarioRep;
	@Autowired
	private CursoRep cursoRep;
	
	public DocenteController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping
	public ResponseEntity<Collection<Docente>> listarDocentes(
			@RequestParam(name="curso", required = false) Integer cursoId, 
			@RequestParam(name="estudiante", required = false) Integer estudianteId){        
		Collection<Docente> docentes = service.listar(cursoId, estudianteId);
		return ResponseEntity.ok(docentes);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Docente> obtenerDocente(@PathVariable("id") Integer docenteId) {
		Docente docente = service.buscarPorId(docenteId);
		if(docente != null) { return ResponseEntity.ok(docente); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR DOCENTE
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> agregarDocente(@RequestBody Docente docente) {
		try {
			if (!cursoRep.existsByCursoId(docente.getCurso().getCursoId())) {
				return new ResponseEntity<>("El curso asignado al docente no se encuentra registrado.", HttpStatus.BAD_REQUEST);
			}
			else if(!(docente.getTelefono()>899999999 && docente.getTelefono()<1000000000)) {
				return new ResponseEntity<>("El formato del número de teléfono no es correcto.", HttpStatus.BAD_REQUEST);
			}
			docente.setFechaRegistro(LocalDate.now());
			service.agregar(docente);
			return new ResponseEntity<Docente>(docente, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Error al agregar el docente: El correo o el teléfono ya se encuentra registrado.", HttpStatus.BAD_REQUEST);
		} catch (NullPointerException e) {
			return new ResponseEntity<>("Error al agregar el docente: Se ingresó un valor nulo para el correo, teléfono o el curso.", HttpStatus.BAD_REQUEST);
		}
	}
	// ACTUALIZAR DOCENTE
	@PreAuthorize("hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarDocente(@RequestBody Docente docente) {
		if(usuarioRep.existsByEmail(docente.getCorreo()) && service.buscarPorCorreo(docente.getCorreo()) != null) {
			service.actualizar(docente);
			return ResponseEntity.ok("Docente actualizado.");
		}
		return ResponseEntity.notFound().build();
	}
}
