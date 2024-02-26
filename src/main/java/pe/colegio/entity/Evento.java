package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity @Table(name = "eventos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "eventoId")
public class Evento implements Serializable{
	private static final long serialVersionUID = 1L;

	public Evento() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eventoId;

	@Column
	private String titulo;
	@Column
	private String descripcion;
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fecha;
	
	@ManyToOne @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

	public Evento(String titulo, String descripcion, LocalDate fecha) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public Integer getEventoId() {
		return eventoId;
	}

	public void setEventoId(Integer eventoId) {
		this.eventoId = eventoId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}