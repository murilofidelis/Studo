package br.com.studo.domain;

import java.sql.Date;

public class Atividade {

	private Long cod_atividade;
	private Integer cod_disciplina;
	private Date data_criacao;
	private String titulo;
	private String descricao;
	private String classificacao;

	private Disciplina disciplina = new Disciplina();

	public Long getCod_atividade() {
		return cod_atividade;
	}

	public void setCod_atividade(Long cod_atividade) {
		this.cod_atividade = cod_atividade;
	}

	public Integer getCod_disciplina() {
		return cod_disciplina;
	}

	public void setCod_disciplina(Integer cod_disciplina) {
		this.cod_disciplina = cod_disciplina;
	}

	public Date getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Date data_criacao) {
		this.data_criacao = data_criacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}
