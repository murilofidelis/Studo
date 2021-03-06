package br.com.studo.domain;

public class Professor {

	private Integer cod_pessoa;
	private Integer cod_tipo;
	private String nome;
	private String cpf;
	private Boolean situacao;
	private String email;
	private String titulo;
	private String sexo;
	private String usuario;
	private String senha;

	public Integer getCod_pessoa() {
		return cod_pessoa;
	}

	public void setCod_pessoa(Integer cod_pessoa) {
		this.cod_pessoa = cod_pessoa;
	}

	public Integer getCod_tipo() {
		return cod_tipo;
	}

	public void setCod_tipo(Integer cod_tipo) {
		this.cod_tipo = cod_tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getSituacao() {
		return situacao;

	}

	public String converterSitacao() {
		String s = null;
		if (situacao == true) {
			s = "Ativo";
		} else if (situacao == false) {
			s = "Desativado";
		}
		return s;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
