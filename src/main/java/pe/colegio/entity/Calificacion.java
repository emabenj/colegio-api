package pe.colegio.entity;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity @Table(name = "calificaciones")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "calificacionId")
public class Calificacion implements Serializable{
	private static final long serialVersionUID = 1L;

	public Calificacion() {}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer calificacionId;

	@Column
	private Integer calificacion1;
	@Column
	private Integer calificacion2;
	@Column
	private Integer calificacion3;
	@Column
	private Integer calificacion4;
	@Column
	private Double calificacionFinal = 0.;
	
	@ManyToOne @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

	@ManyToOne @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

	public Calificacion(Integer calificacion1, Integer calificacion2, Integer calificacion3, Integer calificacion4, Double calificacionFinal) {
		super();
		this.calificacion1 = calificacion1;
		this.calificacion2 = calificacion2;
		this.calificacion3 = calificacion3;
		this.calificacion4 = calificacion4;
		this.calificacionFinal = calificacionFinal;
	}

	public Integer getCalificacionId() {
		return calificacionId;
	}

	public void setCalificacionId(Integer calificacionId) {
		this.calificacionId = calificacionId;
	}

	public Integer getCalificacion1() {
		return calificacion1;
	}

	public void setCalificacion1(Integer calificacion1) {
		this.calificacion1 = calificacion1;
	}

	public Integer getCalificacion2() {
		return calificacion2;
	}

	public void setCalificacion2(Integer calificacion2) {
		this.calificacion2 = calificacion2;
	}

	public Integer getCalificacion3() {
		return calificacion3;
	}

	public void setCalificacion3(Integer calificacion3) {
		this.calificacion3 = calificacion3;
	}

	public Integer getCalificacion4() {
		return calificacion4;
	}

	public void setCalificacion4(Integer calificacion4) {
		this.calificacion4 = calificacion4;
	}

	public Double getCalificacionFinal() {
		return calificacionFinal;
	}

	public void setCalificacionFinal(Double calificacionFinal) {
		this.calificacionFinal = calificacionFinal;
	}	

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}
