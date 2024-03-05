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

@Entity @Table(name = "noticias")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "noticiaId")
public class Noticia implements Serializable{
	private static final long serialVersionUID = 1L;

	public Noticia() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer noticiaId;

	@Column
	private String titulo;
	@Column
	private String contenido;
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaPublicacion = LocalDate.now();
	@Column
	private String ubiImagen;

	@ManyToOne @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

	public Noticia(String titulo, String contenido, String ubiImagen) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.ubiImagen = ubiImagen;
	}

	public Integer getNoticiaId() {
		return noticiaId;
	}

	public void setNoticiaId(Integer noticiaId) {
		this.noticiaId = noticiaId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getUbiImagen() {
		return ubiImagen;
	}

	public void setUbiImagen(String ubiImagen) {
		this.ubiImagen = ubiImagen;
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