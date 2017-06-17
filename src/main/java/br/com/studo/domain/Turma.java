package br.com.studo.domain;

public class Turma {

	private Long cod_turma;
	private String serie;
	private String turma;
	private String sala;
	private String periodo;
	private String ano;

	public Long getCod_turma() {
		return cod_turma;
	}

	public void setCod_turma(Long cod_turma) {
		this.cod_turma = cod_turma;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

}
