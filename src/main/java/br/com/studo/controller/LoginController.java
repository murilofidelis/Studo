package br.com.studo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.studo.dao.DisciplinaDAO;
import br.com.studo.dao.LoginDAO;
import br.com.studo.domain.Disciplina;
import br.com.studo.domain.Pessoa;
import br.com.studo.util.Mensagem;

@ManagedBean
@SessionScoped
public class LoginController {

	private String user;
	private String password;
	public Pessoa pessoaLogada;
	private ArrayList<Disciplina> listaDisciplinas;

	public void autenticar() {
		try {

			LoginDAO dao = new LoginDAO();
			pessoaLogada = dao.autenticar(user, password);
			if (pessoaLogada.isExiste() == false) {
				Messages.addGlobalError(Mensagem.MSG006);
			} else if (pessoaLogada.isExiste() == true && pessoaLogada.getCod_tipo() == 1) {
				Faces.redirect("./templates/sec.xhtml");
			} else if (pessoaLogada.isExiste() == true && pessoaLogada.getCod_tipo() == 2) {
				Faces.redirect("./paginas/pro/atividade.xhtml");
			}

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao autenticar " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean permissoes(Integer permissao) {
		if (pessoaLogada.getCod_tipo() == permissao) {
			return true;
		} else {
			return false;
		}
	}

	public void sair() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			pessoaLogada = null;
			Faces.redirect("./paginas/login.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	// METODO PEGAR O NOME DO USUÁRIO LOGADO
	public String getNomeUsuarioLogado() {
		String nome = pessoaLogada.getNome();
		return nome;
	}

	public void carregarPerfil() {
		DisciplinaDAO dao = new DisciplinaDAO();
		try {
			listaDisciplinas = dao.listarDisciplinasProfessor(pessoaLogada.getCod_pessoa());
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public void alterar() {
		try {
			LoginDAO dao = new LoginDAO();
			dao.alterar(pessoaLogada);
			Messages.addGlobalInfo(Mensagem.MSG004);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	String usuario;
	String senha;

	public void alterarAcesso() {
		try {
			LoginDAO dao = new LoginDAO();
			if (usuario.equals("")) {
				Messages.addGlobalError("O campo Usuário é obrigatório");
				return;
			} else if (senha.equals("")) {
				Messages.addGlobalError("O campo Senha é obrigatório");
				return;
			} else {
				pessoaLogada.setUsuario(usuario);
				pessoaLogada.setSenha(senha);
				String retorno = dao.alterarAcesso(pessoaLogada);
				if (retorno.equals("sucesso")) {
					Messages.addGlobalInfo(Mensagem.MSG004);
				} else if (retorno.contains("usuario_senha_unico")) {
					Messages.addGlobalError(Mensagem.MSG040);
				}
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public Pessoa getPessoaLogada() {
		return pessoaLogada;
	}

	public void setPessoaLogada(Pessoa pessoaLogada) {
		this.pessoaLogada = pessoaLogada;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Disciplina> getListaDisciplinas() {
		return listaDisciplinas;
	}

	public void setListaDisciplinas(ArrayList<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
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
