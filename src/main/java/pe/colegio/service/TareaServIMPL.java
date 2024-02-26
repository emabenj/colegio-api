package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Tarea;
import pe.colegio.repository.TareaRep;

@Service
public class TareaServIMPL implements TareaServ {
	@Autowired
	private TareaRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Tarea buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR TAREAS
	@Override @Transactional(readOnly = true)
	public Collection<Tarea> listar() {
		Collection<Tarea> noticias = repository.findAll();
		return noticias;
	}

	// AGREGAR TAREA
	@Override @Transactional
	public void agregar(Tarea noticia) {
		noticia.setTareaId(null);
		repository.save(noticia);
	}

	// ACTUALIZAR TAREA
	@Override @Transactional
	public void actualizar(Tarea noticia) {
		repository.save(noticia);
	}

	// ELIMINAR TAREA
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}