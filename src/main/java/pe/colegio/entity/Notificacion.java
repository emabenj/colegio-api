package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.EstadoType;

@Entity @Table(name = "notificaciones")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "notificacionId")
public class Notificacion implements Serializable{
	private static final long serialVersionUID = 1L;

	public Notificacion() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer notificacionId;

	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String descripcion;

	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaCreacion = LocalDate.now();
	
	@Column
	private String estado = EstadoType.NO_LEIDA.name();
	
	@Column
	private String tipo = EstadoType.AUTOMATICA.name();

	@JsonBackReference
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuarios_notificaciones", joinColumns=@JoinColumn(name="notificacion_id"), inverseJoinColumns=@JoinColumn(name="usuario_id"))
	private Collection<Usuario> usuarios = new ArrayList();

	public Notificacion(String titulo, String descripcion, Collection<Usuario> usuarios) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.usuarios = usuarios;
	}

	public Integer getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(Integer notificacionId) {
		this.notificacionId = notificacionId;
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

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}