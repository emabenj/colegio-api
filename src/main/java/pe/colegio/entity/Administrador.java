package pe.colegio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity @Table(name = "administradores")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "administradorId")
public class Administrador implements Serializable{
	private static final long serialVersionUID = 1L;

	public Administrador() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer administradorId;
	
	@Column(nullable = false)
	private String nombres;
	@Column(nullable = false)
	private String apellidos;
	
	@Column(unique = true, nullable = false)
	private String correo;
	@Column(unique = true, nullable = false)
	private Integer telefono;

	@OneToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

	@JsonIgnore
	@OneToMany(mappedBy = "administrador")
	private Collection<Noticia> noticias = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "administrador")
	private Collection<Evento> eventos = new ArrayList<>();
	
	public Administrador(String nombres, String apellidos, String correo, Integer telefono) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correo = correo;
		this.telefono = telefono;
	}

	public Integer getAdministradorId() {
		return administradorId;
	}

	public void setAdministradorId(Integer administradorId) {
		this.administradorId = administradorId;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Collection<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(Collection<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Collection<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Collection<Evento> eventos) {
		this.eventos = eventos;
	}
}