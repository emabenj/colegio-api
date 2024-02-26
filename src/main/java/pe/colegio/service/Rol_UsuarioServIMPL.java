package pe.colegio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.colegio.entity.Rol_Usuario;
import pe.colegio.repository.Rol_UsuarioRep;

@Service
public class Rol_UsuarioServIMPL implements Rol_UsuarioServ{
	@Autowired
	private Rol_UsuarioRep repository;

	// BUSCAR ROL by rolId
	@Override @Transactional
	public Rol_Usuario buscarPorId(Integer rolId) {
		return repository.findById(rolId).orElse(null);
	}
	
}
