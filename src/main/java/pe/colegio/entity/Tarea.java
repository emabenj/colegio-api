package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.EstadoType;

@Entity @Table(name = "tareas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tareaId")
public class Tarea implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Tarea() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tareaId;
	
	@Column
	private String descripcion;
	
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaEntrega;

	@Column
	private String estado = EstadoType.ASIGNADA.name();
	
	@ManyToMany @JoinTable(name = "tareas_cursos", joinColumns = @JoinColumn(name="tarea_id"),
			   inverseJoinColumns = @JoinColumn(name="curso_id"))
	private Set<Curso> itemsCurso = new HashSet<>();

	public Tarea(String descripcion, LocalDate fechaEntrega) {
		super();
		this.descripcion = descripcion;
		this.fechaEntrega = fechaEntrega;
	}

	public Integer getTareaId() {
		return tareaId;
	}

	public void setTareaId(Integer tareaId) {
		this.tareaId = tareaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}