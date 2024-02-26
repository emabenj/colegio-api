package pe.colegio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity @Table(name = "apoderados")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "apoderadoId")
public class Apoderado implements Serializable{
	private static final long serialVersionUID = 1L;

	public Apoderado() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer apoderadoId;
	
	@Column(nullable = false)
	private String nombres;
	@Column(nullable = false)
	private String apellidos;
	
	@Column(unique = true, nullable = false)
	private String correo;
	@Column(unique = true, nullable = false)
	private Integer telefono;	
	@Column(nullable = false)
	private String direccion;

	@OneToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "apoderados_estudiantes", joinColumns = @JoinColumn(name="apoderado_id"),
			   inverseJoinColumns = @JoinColumn(name="estudiante_id"))
	private Set<Estudiante> itemsEstudiante = new HashSet<>();

	@OneToMany(mappedBy = "apoderado", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Mensaje> mensajes = new ArrayList<>();
	
	public Apoderado(String nombres, String apellidos, String correo, Integer telefono, String direccion) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correo = correo;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public Integer getApoderadoId() {
		return apoderadoId;
	}

	public void setApoderadoId(Integer ApoderadoId) {
		this.apoderadoId = ApoderadoId;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Estudiante> getItemsEstudiante() {
		return itemsEstudiante;
	}

	public void setItemsEstudiante(Set<Estudiante> itemsEstudiante) {
		this.itemsEstudiante = itemsEstudiante;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}