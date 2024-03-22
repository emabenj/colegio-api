package pe.colegio.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Curso;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Horario;
import pe.colegio.repository.CursoRep;
import pe.colegio.repository.HorarioRep;

@Service
public class HorarioServIMPL implements HorarioServ {
	@Autowired
	private HorarioRep repository;
	@Autowired
	private CursoRep cursoRep;

	// LISTAR HORARIOS
	@Override @Transactional
	public Collection<Horario> listar(LocalDate fechaClase, Collection<Integer> cursoIds) {
		Collection<Horario> horarios = new ArrayList();
		for (Integer cursoId : cursoIds) {
			Collection<Horario>  horariosCurso = repository.findByFechaClaseAndCurso_CursoId(fechaClase, cursoId);
			if (horariosCurso.isEmpty()) {
				Curso curso = cursoRep.findById(cursoId).orElse(null);
				Integer intDocentes = curso.getDocentes().size();
				if(intDocentes != 0) {
					Integer minsXhora = 45;
					Integer minsXcurso = minsXhora * 2;
					
					LocalTime inicio = LocalTime.parse("08:00:00");					
					LocalTime recreoInicio = LocalTime.parse("11:00:00"); LocalTime recreoFin = recreoInicio.plusMinutes(minsXhora);
					LocalTime limite = LocalTime.parse("14:45:00");
					for (int i = 0; i < intDocentes; i++) {
						if(inicio.isBefore(limite)) {
							LocalTime fin = inicio.plusMinutes(minsXcurso);
							Horario nuevoHorario = new Horario(fechaClase, inicio, fin, curso);
							horariosCurso.add(repository.save(nuevoHorario));
							if(fin.equals(recreoInicio)) { fin = recreoFin; }
							inicio = fin;
						}
					}
				}
			}
			horarios.addAll(horariosCurso);
		}

		return horarios;
	}
	public static int countOccurrences(List<Integer> listaNumeros, int numero) {
	    Map<Integer, Integer> mapaRepeticiones = new HashMap<>();
	    for (int num : listaNumeros) {
	        mapaRepeticiones.put(num, mapaRepeticiones.getOrDefault(num, 0) + 1);
	    }
	    return mapaRepeticiones.getOrDefault(numero, 0);
	}
	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Horario buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// AGREGAR DE HORARIO
	@Override @Transactional
	public void agregar(Horario horario) {
		horario.setHorarioId(null);
		repository.save(horario);
	}
	// ACTUALIZAR HORARIO
	@Override @Transactional
	public void actualizar(Horario horario) {
		Horario updateHorario = buscarPorId(horario.getHorarioId());
		updateHorario.setHoraInicio(horario.getHoraInicio());
		updateHorario.setHoraFin(horario.getHoraFin());
		repository.save(horario);
	}
	// ELIMINAR HORARIO
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}