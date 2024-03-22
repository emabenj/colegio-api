package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.EstadoType;

@Entity @Table(name = "estudiantes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "estudianteId")
public class Estudiante implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Estudiante() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer estudianteId;
	
	@Column
	private String nombres;
	@Column
	private String apellidos;
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaNacimiento = LocalDate.now().minusYears(10);
	@Column
	private Character genero;
	@Column(unique = true, nullable = false)
	private Integer dni;
	
	@Column 
	private Integer grado;
	@Column
	private Character seccion;	
	@Column
	private Character nivelEducativo;

	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaInscripcion = LocalDate.now();

	@Column
	private String estado = EstadoType.ACTIVE.name();
	
	@JsonBackReference
	@ManyToMany @JoinTable(name = "estudiantes_cursos", joinColumns = @JoinColumn(name="estudiante_id"),
			   inverseJoinColumns = @JoinColumn(name="curso_id"))
	private Set<Curso> itemsCurso = new HashSet<>();
	
	@OneToMany(mappedBy = "estudiante")
	private Collection<Calificacion> calificaciones = new ArrayList<>();	

	@JsonIgnore
	@ManyToMany(mappedBy = "itemsEstudiante")
	private Set<Asistencia> itemsAsistencia = new HashSet<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "itemsEstudiante")
	private Set<Apoderado> itemsApoderado = new HashSet<>();

	public Estudiante(Integer estudianteId, String nombres, String apellidos, LocalDate fechaNacimiento,
			Character genero, Integer dni, Integer grado, Character seccion, Character nivelEducativo, LocalDate fechaInscripcion,
			String estado) {
		super();
		this.estudianteId = estudianteId;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.dni = dni;
		this.grado = grado;
		this.seccion = seccion;
		this.nivelEducativo = nivelEducativo;
		this.fechaInscripcion = fechaInscripcion;
		this.estado = estado;
	}

	public Integer getEstudianteId() {
		return estudianteId;
	}

	public void setEstudianteId(Integer estudianteId) {
		this.estudianteId = estudianteId;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Character getGenero() {
		return genero;
	}

	public void setGenero(Character genero) {
		this.genero = genero;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public Set<Apoderado> getItemsApoderado() {
		return itemsApoderado;
	}

	public void setItemsApoderado(Set<Apoderado> itemsApoderado) {
		this.itemsApoderado = itemsApoderado;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public Character getSeccion() {
		return seccion;
	}

	public void setSeccion(Character seccion) {
		this.seccion = seccion;
	}

	public Character getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(Character nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public LocalDate getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<Curso> getItemsCurso() {
		return itemsCurso;
	}

	public void setItemsCurso(Set<Curso> itemsCurso) {
		this.itemsCurso = itemsCurso;
	}

	public Collection<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(Collection<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public Set<Asistencia> getItemsAsistencia() {
		return itemsAsistencia;
	}

	public void setItemsAsistencia(Set<Asistencia> itemsAsistencia) {
		this.itemsAsistencia = itemsAsistencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}