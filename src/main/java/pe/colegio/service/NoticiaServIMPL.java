package pe.colegio.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Noticia;
import pe.colegio.repository.NoticiaRep;

@Service
public class NoticiaServIMPL implements NoticiaServ {
	@Autowired
	private NoticiaRep repository;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Noticia buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR NOTICIAS
	@Override @Transactional(readOnly = true)
	public Collection<Noticia> listar() {
		Collection<Noticia> noticias = repository.findAll();
		return noticias;
	}

	// AGREGAR DE NOTICIA
	@Override @Transactional
	public void agregar(Noticia noticia) {
		noticia.setNoticiaId(null);
		repository.save(noticia);
	}

	// ACTUALIZAR NOTICIA
	@Override @Transactional
	public void actualizar(Noticia noticia) {
		repository.save(noticia);
	}

	// ELIMINAR NOTICIA
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}