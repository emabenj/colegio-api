package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Apoderado;
import pe.colegio.entity.Docente;
import pe.colegio.entity.Usuario;

public interface ApoderadoRep extends JpaRepository<Apoderado, Integer> {
	public Optional<Apoderado> findByCorreo(String correo);
	public Boolean existsByCorreo(String correo);
	
	public Collection<Apoderado> findByItemsEstudiante_EstudianteId(Integer estudianteId);
}
