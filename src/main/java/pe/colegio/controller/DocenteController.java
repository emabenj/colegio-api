package pe.colegio.controller;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.*;
import pe.colegio.repository.CursoRep;
import pe.colegio.repository.EstudianteRep;
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
	@Autowired
	private EstudianteRep estudianteRep;
	
	public DocenteController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC') or hasRole('APOD')")
	@GetMapping
	public ResponseEntity<Collection<Docente>> listarDocentes(
			@RequestParam(name="curso", required = false) Integer cursoId, 
			@RequestParam(name="estudiante", required = false) Integer estudianteId){
		HttpHeaders headers = new HttpHeaders();
		Collection<Docente> docentes = new ArrayList<>();
		String msg = "Docentes encontrados";
		if (cursoId != null && cursoRep.findById(cursoId) == null) {
			msg = "Curso no encontrado";
		}else if(estudianteId != null && estudianteRep.findById(estudianteId) == null) {
			msg = "Estudiante no encontrado";
		}else {
			docentes = service.listar(cursoId, estudianteId);
			if (docentes.size()==0) {
				msg = "No hay docentes.";
			}
			headers.set("message", msg);
			List<Docente> sortedDocentes = docentes.stream()
			        .sorted(Comparator.comparing(Docente::getFechaRegistro))
			        .collect(Collectors.toList());
			return ResponseEntity.ok().headers(headers).body(sortedDocentes);
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Docente> obtenerDocente(@PathVariable("id") Integer docenteId) {
		Docente docente = service.buscarPorId(docenteId);
		return docente != null ? ResponseEntity.ok(docente) : ResponseEntity.badRequest().build();
	}
	// AGREGAR DOCENTE
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> agregarDocente(@RequestBody Docente docente) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "";
		try {
			if (!cursoRep.existsByCursoId(docente.getCurso().getCursoId())) {
				msg = "El curso asignado al docente no se encuentra registrado.";
			}
			else if(!(docente.getTelefono()>899999999 && docente.getTelefono()<1000000000)) {
				msg = "El formato del número de teléfono no es correcto.";
			}
			if (msg.isEmpty()) {
				docente.setFechaRegistro(LocalDate.now());
				service.agregar(docente);
				return new ResponseEntity<Docente>(docente, HttpStatus.CREATED);
			}
		} catch (DataIntegrityViolationException e) {
            msg = "El correo o el teléfono ya se encuentra registrado.";
		} catch (NullPointerException e) {
			msg = "Se ingresó un valor nulo para el correo, teléfono o el curso.";
		}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ACTUALIZAR DOCENTE
	@PreAuthorize("hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarDocente(@RequestBody Docente docente) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Docente actualizado.";
		try {
			if(usuarioRep.existsByEmail(docente.getCorreo()) && service.buscarPorCorreo(docente.getCorreo()) != null) {
				service.actualizar(docente);
				headers.set("message", msg);
				return ResponseEntity.ok().headers(headers).build();
			} msg = "No se encontró al docente.";
		} catch (DataIntegrityViolationException e) { msg = "Error al actualizar: Se ingresó un valor duplicado."; } 
		catch (Exception e) { msg = "Error al actualizar el docente: " + e.getMessage(); }
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
}
