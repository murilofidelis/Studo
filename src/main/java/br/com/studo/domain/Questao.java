package br.com.studo.domain;

public class Questao {

	private Long cod_questao;
	private Long cod_atividade;
	private Integer numero;
	private String descricao;
	private Integer tipo;
	private String alt_correta;
	private String alt_a;
	private String alt_b;
	private String alt_c;
	private String alt_d;
	private String alt_e;

	public Long getCod_questao() {
		return cod_questao;
	}

	public void setCod_questao(Long cod_questao) {
		this.cod_questao = cod_questao;
	}

	public Long getCod_atividade() {
		return cod_atividade;
	}

	public void setCod_atividade(Long cod_atividade) {
		this.cod_atividade = cod_atividade;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getAlt_correta() {
		return alt_correta;
	}

	public void setAlt_correta(String alt_correta) {
		this.alt_correta = alt_correta;
	}

	public String getAlt_a() {
		return alt_a;
	}

	public void setAlt_a(String alt_a) {
		this.alt_a = alt_a;
	}

	public String getAlt_b() {
		return alt_b;
	}

	public void setAlt_b(String alt_b) {
		this.alt_b = alt_b;
	}

	public String getAlt_c() {
		return alt_c;
	}

	public void setAlt_c(String alt_c) {
		this.alt_c = alt_c;
	}

	public String getAlt_d() {
		return alt_d;
	}

	public void setAlt_d(String alt_d) {
		this.alt_d = alt_d;
	}

	public String getAlt_e() {
		return alt_e;
	}

	public void setAlt_e(String alt_e) {
		this.alt_e = alt_e;
	}

}
