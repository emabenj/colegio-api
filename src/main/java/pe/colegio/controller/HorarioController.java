package pe.colegio.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.colegio.entity.Horario;
import pe.colegio.service.*;

@RestController @RequestMapping("/horarios")
public class HorarioController {
	@Autowired
	private HorarioServ service;
	
	public HorarioController() {}

	// LISTAR
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD')")
	@PostMapping
	public ResponseEntity<Collection<Horario>> listarHorarios(
			@RequestParam(value = "fecha") String fecha,
			@RequestBody Collection<Integer> cursoIds){
		Collection<Horario> horarios = service.listar(LocalDate.parse(fecha), cursoIds);
		List<Horario> sortedHorarios = horarios.stream()
		        .sorted(Comparator.comparing(Horario::getHoraInicio))
		        .collect(Collectors.toList());
		System.out.println("OK:" + sortedHorarios.size());
		
		return ResponseEntity.ok(sortedHorarios);
	}
	// BUSCAR POR ID
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@GetMapping("/{id}")
	public ResponseEntity<Horario> obtenerHorario(@PathVariable("id") Integer id) {
		Horario horario = service.buscarPorId(id);
		if(horario != null) { return ResponseEntity.ok(horario); }
		return ResponseEntity.notFound().build();
	}
	// AGREGAR HORARIO
//	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
//	@PostMapping
//	public ResponseEntity<Horario> agregarHorario(@RequestBody Horario horario) {
//		service.agregar(horario);
//		return new ResponseEntity<Horario>(horario, HttpStatus.CREATED);
//	}
	// ACTUALIZAR HORARIO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<?> actualizarHorario(@RequestBody Horario horario) {
		HttpHeaders headers = new HttpHeaders();
		String msg = "Horario actualizado.";
		try {
			if(service.buscarPorId(horario.getHorarioId()) != null) {
				service.actualizar(horario);
				headers.set("message", msg);
				return ResponseEntity.ok().headers(headers).build();
			}else {
				msg = "El horario con ese id no existe.";
			}
		} catch (DataIntegrityViolationException e) { msg = "Se ingresó un valor duplicado."; } 
		catch (Exception e) { msg = e.getMessage(); }
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	// ELIMINAR HORARIO
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOC')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarHorario(@PathVariable("id") Integer id) {
		if(service.buscarPorId(id) != null) {
//			String msg = "Este horario no puede ser eliminado.";
			service.eliminar(id);
			return ResponseEntity.ok("Horario eliminado");
		}
		return ResponseEntity.notFound().build();
	}
}