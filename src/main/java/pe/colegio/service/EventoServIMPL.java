package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Evento;
import pe.colegio.repository.EventoRep;

@Service
public class EventoServIMPL implements EventoServ {
	@Autowired
	private EventoRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Evento buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR EVENTOS
	@Override @Transactional(readOnly = true)
	public Collection<Evento> listar() {
		Collection<Evento> eventos = repository.findAll();
		return eventos;
	}

	// AGREGAR CONJUNTO DE EVENTOS
	@Override @Transactional
	public void agregar(Evento evento) {
		evento.setEventoId(null);
		repository.save(evento);
	}

	// ACTUALIZAR CONJUNTO DE EVENTOS
	@Override @Transactional
	public void actualizar(Evento evento) {
		repository.save(evento);
	}

	// ELIMINAR CONJUNTO DE EVENTOS
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}