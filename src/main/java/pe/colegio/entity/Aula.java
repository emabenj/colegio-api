package pe.colegio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Aula implements Serializable{
	private static final long serialVersionUID = 1L;

	public Aula() {}
	
	private Collection<Estudiante> estudiantes = new ArrayList<>();
	private String grado;
	private String seccion;
	private Collection<Apoderado> apoderados = new ArrayList<>();
	private Collection<String> emailsApoderados = new ArrayList<>();

//	public Aula(Collection<Estudiante> estudiantes, String grado, String seccion, Collection<Apoderado> apoderados) {
//		super();
//		this.estudiantes = estudiantes;
//		this.grado = grado;
//		this.seccion = seccion;
//		this.apoderados = apoderados;
//	}
	public Aula(Collection<Estudiante> estudiantes, String grado, String seccion, Collection<String> emailsApoderados) {
		super();
		this.estudiantes = estudiantes;
		this.grado = grado;
		this.seccion = seccion;
		this.emailsApoderados = emailsApoderados;
	}

	public Collection<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(Collection<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public Collection<Apoderado> getApoderados() {
		return apoderados;
	}

	public void setApoderados(Collection<Apoderado> apoderados) {
		this.apoderados = apoderados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Collection<String> getEmailsApoderados() {
		return emailsApoderados;
	}
	public void setEmailsApoderados(Collection<String> emailsApoderados) {
		this.emailsApoderados = emailsApoderados;
	}

}