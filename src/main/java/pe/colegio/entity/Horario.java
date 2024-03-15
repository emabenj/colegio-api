package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity @Table(name = "horarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "horarioId")
public class Horario implements Serializable{
	private static final long serialVersionUID = 1L;

	public Horario() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer horarioId;

	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaClase;
	@Column @DateTimeFormat(pattern="HH:mm",iso=ISO.TIME)
	private LocalTime horaInicio;
	@Column @DateTimeFormat(pattern="HH:mm",iso=ISO.TIME)
	private LocalTime horaFin;

	@ManyToOne @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

	public Horario(LocalDate fechaClase, LocalTime horaInicio, LocalTime horaFin, Curso curso) {
		super();
		this.fechaClase = fechaClase;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.curso = curso;
	}

	public Integer getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(Integer horarioId) {
		this.horarioId = horarioId;
	}

	public LocalDate getFechaClase() {
		return fechaClase;
	}

	public void setFechaClase(LocalDate fechaClase) {
		this.fechaClase = fechaClase;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}