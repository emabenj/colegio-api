package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.EstadoType;

@Entity
@Table(name = "asistencias")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "asistenciaId")
public class Asistencia implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Asistencia() {}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer asistenciaId;

	@Column
	private String estado = EstadoType.F.name();
	
	@DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fecha;

	@Column
	private String justificacion = "";

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "asistencias_estudiantes", joinColumns = @JoinColumn(name="asistencia_id"),
			inverseJoinColumns = @JoinColumn(name="estudiante_id"))
	private Set<Estudiante> itemsEstudiante = new HashSet<>();
	

	public Asistencia(LocalDate fecha) {
		super();
		this.fecha = fecha;
	}


	public Integer getAsistenciaId() {
		return asistenciaId;
	}

	public void setAsistenciaId(Integer asistenciaId) {
		this.asistenciaId = asistenciaId;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Set<Estudiante> getItemsEstudiante() {
		return itemsEstudiante;
	}

	public void setItemsEstudiante(Set<Estudiante> itemsEstudiante) {
		this.itemsEstudiante = itemsEstudiante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}