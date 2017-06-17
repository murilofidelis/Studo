package br.com.studo.domain;



import java.sql.Date;

public class Aluno {

	private Long matricula;
	private String nome;
	private String email;
	private String sexo;
	private Boolean situacao;
	private Date data_nascimento;
	private String usuario;
	private String senha;

	private Turma turma;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String converterSexo() {
		String s = null;
		if (sexo.equals("M")) {
			s = "Masculino";
		} else if (sexo.equals("F")) {
			s = "Feminino";
		}
		return s;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
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

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
