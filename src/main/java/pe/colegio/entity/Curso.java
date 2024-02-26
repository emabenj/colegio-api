package pe.colegio.entity;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity @Table(name = "cursos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cursoId")
public class Curso implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Curso() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cursoId;
	
	@Column
	private String nombre;
	@Column
	private Character nivelEducativo;
	@Column
	private String dia;

	@JsonIgnore
	@ManyToMany(mappedBy = "itemsCurso")
	private Set<Estudiante> itemsEstudiante = new HashSet<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "itemsCurso")
	private Set<Tarea> itemsTareas = new HashSet<>();
	
	@OneToMany(mappedBy = "curso")
	private Collection<Docente> docentes = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "curso")
	private Collection<Calificacion> calificaciones = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "curso")
	private Collection<Horario> horarios = new ArrayList<>();

	public Curso(String nombre, Character nivelEducativo, String dia) {
		super();
		this.nombre = nombre;
		this.nivelEducativo = nivelEducativo;
		this.dia = dia;
	}

	public Curso(Integer cursoId) {
		super();
		this.cursoId = cursoId;
	}

	public Integer getCursoId() {
		return cursoId;
	}

	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(Collection<Docente> docentes) {
		this.docentes = docentes;
	}

	public Collection<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(Collection<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public Set<Estudiante> getItemsEstudiante() {
		return itemsEstudiante;
	}

	public void setItemsEstudiante(Set<Estudiante> itemsEstudiante) {
		this.itemsEstudiante = itemsEstudiante;
	}

	public Character getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(Character nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Set<Tarea> getItemsTareas() {
		return itemsTareas;
	}

	public void setItemsTareas(Set<Tarea> itemsTareas) {
		this.itemsTareas = itemsTareas;
	}

	public Collection<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(Collection<Horario> horarios) {
		this.horarios = horarios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}