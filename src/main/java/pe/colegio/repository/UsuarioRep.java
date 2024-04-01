package pe.colegio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Usuario;

public interface UsuarioRep extends JpaRepository<Usuario, Integer>{
	public Optional<Usuario> findByEmail(String correo);
	public Boolean existsByEmail(String correo);
	
	@Query("SELECT CASE WHEN COUNT(*) = :totalIds THEN true ELSE false END FROM Usuario u WHERE u.usuarioId IN :ids")
	public Boolean existsAllByIds(@Param("ids") List<Integer> ids, @Param("totalIds") Long totalIds);
}