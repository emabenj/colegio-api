package pe.colegio.service;

import java.time.LocalDate;
import java.util.Collection;

import pe.colegio.entity.Asistencia;


public interface AsistenciaServ {
	public abstract Asistencia buscarPorId(Integer id);
//	public abstract Collection<Asistencia> listar(Integer alumnoId);
//	public abstract Collection<Asistencia> listaByFI(LocalDate fi);
//	public abstract void agregar(Asistencia asistencia);
//	public abstract void actualizar(Asistencia asistencia);
//	public abstract void eliminar(Integer id);
}
