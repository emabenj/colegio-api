package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

	@Column
	private String dia;
	@Column @DateTimeFormat(pattern="HH:mm",iso=ISO.TIME)
	private LocalDate horaInicio;
	@Column @DateTimeFormat(pattern="HH:mm",iso=ISO.TIME)
	private LocalDate horaFin;

	@ManyToOne @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

	public Horario(String dia, LocalDate horaInicio, LocalDate horaFin) {
		super();
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public Integer getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(Integer horarioId) {
		this.horarioId = horarioId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public LocalDate getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalDate horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalDate getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalDate horaFin) {
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