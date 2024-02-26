package pe.colegio.util;

import pe.colegio.entity.*;

public class FiltroCD {
	public FiltroCD() {}
	
	private Curso curso;
	private Docente docente;
	
	public FiltroCD(Curso curso, Docente docente) {
		super();
		this.curso = curso;
		this.docente = docente;
	}

	public Curso getCurso() {
		return curso;
	}

	public Docente getDocente() {
		return docente;
	}
}