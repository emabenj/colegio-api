package pe.colegio.service;

import java.time.LocalDate;
import java.util.Collection;

import pe.colegio.entity.Asistencia;
import pe.colegio.entity.Estudiante;


public interface AsistenciaServ {
	public abstract Asistencia buscarPorId(Integer id);
	public abstract Asistencia buscarPorIdYEstudiante(Integer estudianteId, LocalDate fecha);
	public abstract Collection<Asistencia> listar(LocalDate fecha, Collection<Estudiante> estudiantes);
//	public abstract Collection<Asistencia> listaByFI(LocalDate fi);
//	public abstract void agregar(Asistencia asistencia);
//	public abstract void actualizar(Asistencia asistencia);
	public abstract Collection<String> actualizarAsistencias(Collection<Asistencia> asistencias);
//	public abstract void eliminar(Integer id);
}
