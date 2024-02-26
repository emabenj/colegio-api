package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Mensaje;
import pe.colegio.repository.MensajeRep;

@Service
public class MensajeServIMPL implements MensajeServ {
	@Autowired
	private MensajeRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Mensaje buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR MENSAJES
	@Override @Transactional(readOnly = true)
	public Collection<Mensaje> listar() {
		Collection<Mensaje> mensajes = repository.findAll();
		return mensajes;
	}

	// AGREGAR DE MENSAJE
	@Override @Transactional
	public void agregar(Mensaje mensaje) {
		mensaje.setMensajeId(null);
		repository.save(mensaje);
	}

	// ACTUALIZAR MENSAJE
	@Override @Transactional
	public void actualizar(Mensaje mensaje) {
		repository.save(mensaje);
	}

	// ELIMINAR MENSAJE
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}