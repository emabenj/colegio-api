package pe.colegio.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.colegio.entity.Docente;
import pe.colegio.entity.Usuario;

public interface DocenteRep extends JpaRepository<Docente, Integer> {
	public Optional<Docente> findByCorreo(String correo);
	public Boolean existsByCorreo(String correo);
	
	public Collection<Docente> findByCurso_CursoId(Integer cursoId);
	public Collection<Docente> findByCurso_ItemsEstudiante_EstudianteId(Integer estudianteId);
	
	//Filtrar todos los cursos
//	@Query(value = "SELECT * FROM docentes d INNER JOIN cursos c ON c.curso_id = d.curso_id "
//			+ "WHERE ((:nombres IS Null) OR (d.nombres LIKE CONCAT('%',:nombres,'%'))) "
//			+ "AND ((:apellidos IS NULL) OR (d.apellidoP LIKE CONCAT('%',:apellidos,'%')) OR (d.apellidoM LIKE CONCAT('%',:apellidos,'%'))) "
//			+ "AND (:sexo IS NULL OR d.sexo=:sexo) AND (:fechaRegistro IS NULL OR d.f_registro=:fechaRegistro) AND (d.estado != 'DELETED') "
//			+ "AND (:niv_edu IS NULL OR c.niv_educativo=:niv_edu) AND (:turno IS NULL OR c.turno=:turno)",
//            nativeQuery = true)
//	Collection<Docente> findByFiltrosWC(@Param("nombres") String nombres, @Param("apellidos") String apellidos,
//                           				@Param("sexo") String sexo, @Param("fechaRegistro") LocalDate fechaRegistro,
//                           				@Param("niv_edu") String nivel_educativo, @Param("turno") String turno);
//		
//	@Query(value = "SELECT * FROM docentes d INNER JOIN cursos c ON c.curso_id = d.curso_id "
//			+ "WHERE (d.curso_id =:idc) AND ((:nombres IS null) OR (d.nombres LIKE CONCAT('%',:nombres,'%'))) "
//			+ "AND ((:apellidos IS NULL) OR (d.apellidoP LIKE CONCAT('%',:apellidos,'%')) OR (d.apellidoM LIKE CONCAT('%',:apellidos,'%'))) "
//			+ "AND (:sexo IS NULL OR d.sexo=:sexo) AND (:fechaRegistro IS NULL OR d.f_registro=:fechaRegistro) AND (d.estado != 'DELETED') "
//			+ "AND (:niv_edu IS NULL OR c.niv_educativo=:niv_edu) AND (:turno IS NULL OR c.turno=:turno)",			
//            nativeQuery = true)
//    Collection<Docente> findByFiltrosCC(@Param("idc") Integer idc, @Param("nombres") String nombres, @Param("apellidos") String apellidos,
//                                        @Param("sexo") String sexo, @Param("fechaRegistro") LocalDate fechaRegistro,
//    									@Param("niv_edu") String nivel_educativo, @Param("turno") String turno);
//	
}
