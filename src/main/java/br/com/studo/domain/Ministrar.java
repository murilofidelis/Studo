package br.com.studo.domain;

public class Ministrar {

	private Integer codProfessor;
	private Integer codDisciplina;
	private Integer ano;
	private String descDisciplina;

	public Integer getCodDisciplina() {
		return codDisciplina;
	}

	public void setCodDisciplina(Integer codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getCodProfessor() {
		return codProfessor;
	}

	public void setCodProfessor(Integer codProfessor) {
		this.codProfessor = codProfessor;
	}

	public String getDescDisciplina() {
		return descDisciplina;
	}

	public void setDescDisciplina(String descDisciplina) {
		this.descDisciplina = descDisciplina;
	}
}
