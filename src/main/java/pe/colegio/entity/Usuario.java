package pe.colegio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pe.colegio.util.EstadoType;

@Entity
@Table(name = "usuarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "usuarioId")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;

	public Usuario() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer usuarioId;

	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String contrasena;
	
	@Column(unique = true, nullable = false)
	private Integer telefono;
	
	@Column
	private String nombres;	
	@Column
	private String apellidos;	
	
	@Column
	private String estado = EstadoType.INACTIVE.name();

	@OneToOne(mappedBy = "usuario")
    private Docente docente;

	@OneToOne(mappedBy = "usuario")
    private Apoderado apoderado;
	
	@OneToOne(mappedBy = "usuario")
    private Administrador administrador;
    
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuarios_roles", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Rol_Usuario> itemsRole = new HashSet<>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuarios_notificaciones", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="notificacion_id"))
	private List<Notificacion> notificaciones = new ArrayList();
	
	public Boolean tieneRol(String rol) {
		Boolean evaluacion = false;
		for (Rol_Usuario r : itemsRole) {
			evaluacion = r.getNombre().equals(rol);
		}
		return evaluacion;
	}
	
	public Usuario(String email, String contrasena) {
		super();
		this.email = email;
		this.contrasena = contrasena;
	}

	public Usuario(String email, String contrasena, Integer telefono, String nombres, String apellidos) {
		super();
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;
		this.nombres = nombres;
		this.apellidos = apellidos;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.email = docente.getCorreo();
		this.contrasena = docente.getTelefono().toString();
		this.telefono = docente.getTelefono();
		this.nombres = docente.getNombres();
		this.apellidos = docente.getApellidos();
		
		this.docente = docente;
	}

	public Apoderado getApoderado() {
		return apoderado;
	}

	public void setApoderado(Apoderado apoderado) {
		this.email = apoderado.getCorreo();
		this.contrasena = apoderado.getTelefono().toString();
		this.telefono = apoderado.getTelefono();
		this.nombres = apoderado.getNombres();
		this.apellidos = apoderado.getApellidos();
		
		this.apoderado = apoderado;
	}

	public Set<Rol_Usuario> getItemsRole() {
		return itemsRole;
	}

	public void setItemsRole(Set<Rol_Usuario> itemsRole) {
		this.itemsRole = itemsRole;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
}