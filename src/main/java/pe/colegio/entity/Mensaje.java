package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "mensajes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mensajeId")
public class Mensaje implements Serializable{
	private static final long serialVersionUID = 1L;

	public Mensaje() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mensajeId;

	@Column(nullable = false)
	private String contenido;
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE_TIME)
	private LocalDate fechaHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apoderado_id", nullable = false)
    private Apoderado apoderado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

	public Mensaje(String contenido, LocalDate fechaHora) {
		super();
		this.contenido = contenido;
		this.fechaHora = fechaHora;
	}

	public Integer getMensajeId() {
		return mensajeId;
	}

	public void setMensajeId(Integer mensajeId) {
		this.mensajeId = mensajeId;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Apoderado getApoderado() {
		return apoderado;
	}

	public void setApoderado(Apoderado apoderado) {
		this.apoderado = apoderado;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}