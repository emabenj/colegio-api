package pe.colegio.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Estudiante;
import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Usuario;
import pe.colegio.service.AsistenciaServ;
import pe.colegio.service.DocenteServ;
import pe.colegio.service.EstudianteServ;
import pe.colegio.service.UsuarioServ;

@Controller @RequestMapping("/asistencias")
public class AsistenciaController {
	@Autowired
	private AsistenciaServ service;
//	@Autowired
//	private UsuarioServ usuarioService;
//	@Autowired
//	private DocenteServ docenteService;
//	@Autowired
//	private EstudianteServ estudianteService;
	
	public AsistenciaController() {}
	
	private LocalDate FInicio = LocalDate.parse("2024-04-03");
	private Set<Estudiante> tempItemsAl = new HashSet<Estudiante>();
	

	// LISTAR ASISTENCIAS
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD') or hasRole('DOC')")
	@PostMapping
	public ResponseEntity<Collection<Asistencia>> listarAsistencias(
			@RequestParam(value = "fecha") String fecha,
			@RequestBody Collection<Estudiante> estudiantes){
		Collection<Asistencia> asistencias = service.listar(LocalDate.parse(fecha), estudiantes);
		return ResponseEntity.ok(asistencias);
	}
	// ACTUALIZAR ASISTENCIAS
	@PreAuthorize("hasRole('ADMIN') or hasRole('APOD') or hasRole('DOC')")
	@PutMapping
	public ResponseEntity<Collection<String>> actualizarAsistencias(@RequestBody Collection<Asistencia> asistencias){
		HttpHeaders headers = new HttpHeaders();
		String msg = "No se ha actualizado ninguna noticia.";
		Collection<String> emailsApoderados = service.actualizarAsistencias(asistencias);
		if(emailsApoderados.size() == 0) {
			msg = "No hay asistencias para actualizar";
			headers.set("message", msg);
		}else if(emailsApoderados.size() != asistencias.size()) {
			Integer total = asistencias.size() - emailsApoderados.size();
			msg = "Las asistencias de " + total.toString() + " alumnos no se actualizaron correctamente.";			
		}else { 
			msg = "Asistencias actualizadas.";
			headers.set("message", msg);
			return ResponseEntity.ok().headers(headers).body(emailsApoderados);
			}
		headers.set("message", msg);
		return ResponseEntity.badRequest().headers(headers).build();
	}
	//AGREGAR - EDITAR
//	@GetMapping("/estudiante/{id}/{sem}")
//	public String controlAsistenciasEstudiante_GET(Model model, Map map, @PathVariable("id")Integer estudianteId, @PathVariable("sem")Integer sem) {
//		//NECESARIO
//		LocalDate fecha_inicio = FInicio.plusDays((sem-1)*7);
//		Collection<Asistencia> asistencias = service.listaByFI(fecha_inicio);
//		
//		Asistencia astnadd = new Asistencia();
//		if(asistencias.size() == 0) {
//			//ASIGNAR Estudiante
//			Set<Estudiante> estudiantes = new HashSet<Estudiante>(); estudiantes.add(estudianteService.buscarPorId(estudianteId));
//			//AGREGAR FECHAS
//			for (int i = 0; i < 5; i++) {
//				astnadd = new Asistencia(fecha_inicio.plusDays(i), "", "", estudiantes);
//				asistencias.add(astnadd);
//				service.agregar(astnadd);
//	        }
//		}
//		tempItemsAl = asistencias.stream().findFirst().get().getItemsEstudiante();
//		map.put("ctnA", asistencias.size());
//		int i = 0;
//		for (Asistencia asist : asistencias) {
//			model.addAttribute("mdlAsistencia"+(i+1), asist);
//			if(i == 0 || i == 4){
//				map.put("dia"+(i+1), asist.getFecha().getDayOfMonth());
//				map.put("mes", asist.getFecha().getMonth());
//			}
//			i+=1;
//		}
//		return "Asistencia/Control";
//		return "redirect:/home/";
//	}
	//JS
//	@PostMapping("/estudiante/{id}/{sem}")
//	public String controlAsistenciasEstudiante_POST(@ModelAttribute("mdlAsistencia1") Asistencia a1, @ModelAttribute("mdlAsistencia2") Asistencia a2,
//												@ModelAttribute("mdlAsistencia3") Asistencia a3, @ModelAttribute("mdlAsistencia4") Asistencia a4,
//												@ModelAttribute("mdlAsistencia5") Asistencia a5) {		
//		for (Asistencia asistencia : Arrays.asList(a1, a2, a3, a4, a5)) {
//			service.actualizar(asistencia);
//		}
//		return "redirect:/asistencias/";
//	}
//	@PostMapping("/guardar-asistencias")
//	public void guardarAsistencias(@RequestBody List<Asistencia> asistencias) {
//	    for (Asistencia asistencia : asistencias) {
//	    	System.out.println("ID" + " " +asistencia.getAsistenciaId());
//	    	asistencia.setItemsEstudiante(new HashSet<>(tempItemsAl));
//	        service.actualizar(asistencia);
//	    }
//	}
}