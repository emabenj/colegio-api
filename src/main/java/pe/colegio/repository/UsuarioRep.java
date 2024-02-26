package pe.colegio.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import pe.colegio.entity.Usuario;

public interface UsuarioRep extends JpaRepository<Usuario, Integer>{
	public Optional<Usuario> findByEmail(String correo);
	public Boolean existsByEmail(String correo);
}