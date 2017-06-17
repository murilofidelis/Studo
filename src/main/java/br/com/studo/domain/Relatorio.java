package br.com.studo.domain;

import java.sql.Date;

public class Relatorio {

	private Long matricula;
	private String nome;
	private Double nota;
	private int qtd;

	// campos da tela de detalhes
	private Integer numero;
	private String decricaoQuestao;
	private String altCorreta;
	private String altEscolhida;
	private Date dataResposta;

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDecricaoQuestao() {
		return decricaoQuestao;
	}

	public void setDecricaoQuestao(String decricaoQuestao) {
		this.decricaoQuestao = decricaoQuestao;
	}

	public String getAltCorreta() {
		return altCorreta;
	}

	public void setAltCorreta(String altCorreta) {
		this.altCorreta = altCorreta;
	}

	public String getAltEscolhida() {
		return altEscolhida;
	}

	public void setAltEscolhida(String altEscolhida) {
		this.altEscolhida = altEscolhida;
	}

	public String verificarAcerto() {
		String acerto;
		if (altEscolhida.equals(altCorreta)) {
			acerto = "correto";
		} else {
			acerto = "errado";
		}
		return acerto;
	}

	public Date getDataResposta() {
		return dataResposta;
	}

	public void setDataResposta(Date dataResposta) {
		this.dataResposta = dataResposta;
	}

}
