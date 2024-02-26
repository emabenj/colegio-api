package pe.colegio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.*;

@Entity @Table(name = "docentes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "docenteId")
public class Docente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Docente() {}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer docenteId;
	
	@Column(nullable = false)
	private String nombres;	
	@Column(nullable = false)
	private String apellidos;
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaNacimiento;
	
	@Column
	private Character genero;
	@Column(unique = true, nullable = false)
	private String correo;
	@Column(unique = true, nullable = false)
	private Integer telefono;
	@Column
	private String direccion;
	
	@Column @DateTimeFormat(pattern="yyyy-MM-dd",iso=ISO.DATE)
	private LocalDate fechaRegistro;
	
	@ManyToOne @JoinColumn(name = "curso_id", nullable = false)
	private Curso curso;

	@OneToOne @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	@OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Mensaje> mensajes = new ArrayList<>();

	public Docente(String nombres, String apellidos, LocalDate fechaNacimiento, Character genero,
			String direccion, Integer telefono, String correo, LocalDate fechaRegistro, String estado, Curso curso) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
		this.fechaRegistro = fechaRegistro;
		this.curso = curso;
	}

	public Integer getDocenteId() {
		return docenteId;
	}

	public void setDocenteId(Integer docenteId) {
		this.docenteId = docenteId;
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Character getGenero() {
		return genero;
	}

	public void setGenero(Character genero) {
		this.genero = genero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer celular) {
		this.telefono = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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
}