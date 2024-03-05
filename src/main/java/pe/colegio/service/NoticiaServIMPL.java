package pe.colegio.service;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.colegio.entity.Noticia;
import pe.colegio.repository.AdministradorRep;
import pe.colegio.repository.NoticiaRep;

@Service
public class NoticiaServIMPL implements NoticiaServ {
	@Autowired
	private NoticiaRep repository;
	@Autowired
	private AdministradorRep adminRep;

	// BUSCAR POR ID
	@Override @Transactional(readOnly = true)
	public Noticia buscarPorId(Integer id) {
		return repository.findById(id).orElse(null);
	}
	// LISTAR NOTICIAS
	@Override @Transactional
	public Collection<Noticia> listar() {
		LocalDate hace1Mes = LocalDate.now().minusMonths(1);
		Collection<Noticia> noticiasAEliminar = repository.findByFechaPublicacionBefore(hace1Mes);
		Integer noticiasTotal = repository.findAll().size();

		System.out.println("Cantida de de noticias iniciales: "+String.valueOf(noticiasTotal));
		if(noticiasAEliminar.size() > 0) {
			System.out.println("Se eliminarán "+String.valueOf(noticiasAEliminar.size())+" noticias que son de hace 1 mes.");
			repository.deleteByFechaPublicacionBefore(hace1Mes);
			System.out.println("Se eliminaron las noticias...");
		}
		Collection<Noticia> noticias = repository.findByOrderByFechaPublicacionDesc();
		System.out.println("Se mostrarán "+String.valueOf(noticias.size())+" noticias recientes.");
		return noticias;
	}

	// AGREGAR DE NOTICIA
	@Override @Transactional
	public Noticia agregar(Noticia noticia) {
		noticia.setNoticiaId(null);
		noticia.setFechaPublicacion(LocalDate.now());
		noticia.setAdministrador(adminRep.findById(noticia.getAdministrador().getAdministradorId()).orElse(null));
		return repository.save(noticia);
	}

	// ACTUALIZAR NOTICIA
	@Override @Transactional
	public void actualizar(Noticia noticia) {
		noticia.setFechaPublicacion(LocalDate.now());
		repository.save(noticia);
	}

	// ELIMINAR NOTICIA
	@Override @Transactional
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
}