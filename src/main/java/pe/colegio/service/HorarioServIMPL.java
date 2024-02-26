package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Horario;
import pe.colegio.repository.HorarioRep;

@Service
public class HorarioServIMPL implements HorarioServ {
	@Autowired
	private HorarioRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Horario buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR HORARIOS
	@Override @Transactional(readOnly = true)
	public Collection<Horario> listar() {
		Collection<Horario> horarios = repository.findAll();
		return horarios;
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
		repository.save(horario);
	}

	// ELIMINAR HORARIO
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}