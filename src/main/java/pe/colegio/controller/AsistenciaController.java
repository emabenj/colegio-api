package pe.colegio.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Estudiante;
import pe.colegio.entity.Usuario;
import pe.colegio.service.AsistenciaServ;
import pe.colegio.service.DocenteServ;
import pe.colegio.service.EstudianteServ;
import pe.colegio.service.UsuarioServ;

//@Controller @RequestMapping("/asistencias")
public class AsistenciaController {
//	@Autowired
//	private AsistenciaServ service;
//	@Autowired
//	private UsuarioServ usuarioService;
//	@Autowired
//	private DocenteServ docenteService;
//	@Autowired
//	private EstudianteServ estudianteService;
	
	public AsistenciaController() {}
	
	private LocalDate FInicio = LocalDate.parse("2024-04-03");
	private Set<Estudiante> tempItemsAl = new HashSet<Estudiante>();
	
	//LISTAR
	@GetMapping("")
	public String listarEstudiantes_GET() { 
		return "redirect:/asistencias/";
	}
	
//	@GetMapping("/")
//	public String listarEstudiantes_GET(Map map) {		
//		map.put("estudiantesL", estudianteService.listarPorCurso(null));
//		return "Asistencia/Listar";
//		return "redirect:/home/";
//	}
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