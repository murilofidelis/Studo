package br.com.studo.domain;

import java.sql.Date;

public class Atribuicao {

	private Long cod_turma;
	private Long cod_atividade;
	private Double valor;
	private Date date_resposta;
	private Boolean situacao;
	private Integer qtdQuestoes;

	Turma turma = new Turma();

	public Integer getQtdQuestoes() {
		return qtdQuestoes;
	}

	public void setQtdQuestoes(Integer qtdQuestoes) {
		this.qtdQuestoes = qtdQuestoes;
	}

	public Long getCod_turma() {
		return cod_turma;
	}

	public void setCod_turma(Long cod_turma) {
		this.cod_turma = cod_turma;
	}

	public Long getCod_atividade() {
		return cod_atividade;
	}

	public void setCod_atividade(Long cod_atividade) {
		this.cod_atividade = cod_atividade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDate_resposta() {
		return date_resposta;
	}

	public void setDate_resposta(Date date_resposta) {
		this.date_resposta = date_resposta;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public String formatarSituacao() {
		String s = null;
		if (situacao == true) {
			s = "Encerrada";
		} else if (situacao == false) {
			s = "Publicada";
		}
		return s;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
