package br.com.studo.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.studo.dao.DisciplinaDAO;
import br.com.studo.dao.MinistrarDAO;
import br.com.studo.dao.ProfessorDAO;
import br.com.studo.domain.Disciplina;
import br.com.studo.domain.Ministrar;
import br.com.studo.domain.Professor;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class ProfessorController {

	private Professor professor;
	private ListDataModel<Professor> professores;
	private String nomeprofessor;
	private boolean exibir = false;
	private List<Disciplina> disciplinas;
	private Ministrar ministrar;
	private ListDataModel<Ministrar> ministrars;

	@PostConstruct
	public void listar() {
		try {
			ProfessorDAO dao = new ProfessorDAO();
			ArrayList<Professor> lista;
			lista = dao.listarProfessor();
			professores = new ListDataModel<>(lista);

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
		}
	}

	public void buscarNome() {
		try {
			ProfessorDAO dao = new ProfessorDAO();
			ArrayList<Professor> lista;
			lista = dao.listarPorNome(nomeprofessor);
			professores = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public void novo() {
		professor = new Professor();
		professor.setCod_tipo(2);
		professor.setSituacao(true);
	}

	public void salvar() {
		try {
			ProfessorDAO dao = new ProfessorDAO();
			ArrayList<String> cpfs = new ArrayList<>();
			boolean cpfValido = isCPF(professor.getCpf());
			if (cpfValido) {
				cpfs = dao.verificarCPFInsert();
				if (cpfs.contains(professor.getCpf().toString())) {
					Messages.addGlobalWarn(Mensagem.MSG026);
				} else {
					dao.salvar(professor);
					Messages.addGlobalInfo(Mensagem.MSG003);
					RequestContext.getCurrentInstance().execute("PF('dlgCadastro').hide();");
					listar();
				}
			} else {
				Messages.addGlobalWarn(Mensagem.MSG054);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public void getRowEdit() {
		professor = professores.getRowData();
	}

	public void editar() {
		try {
			ProfessorDAO dao = new ProfessorDAO();
			dao.editar(professor);
			Messages.addGlobalInfo(Mensagem.MSG004);
			RequestContext.getCurrentInstance().execute("PF('dlgEdicao').hide();");
		} catch (SQLException e) {
			Messages.addGlobalWarn(Mensagem.MSG026);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		} finally {
			listar();
		}
	}

	String usuario;
	String senha;

	public void editarAcesso() {
		try {
			if (usuario.equals("") && !senha.equals("")) {
				Messages.addGlobalError("O Campo Usuário é obrigatório");
			} else if (!usuario.equals("") && senha.equals("")) {
				Messages.addGlobalError("O Campo Senha é obrigatório");
			} else if (usuario.equals("") || senha.equals("")) {
				Messages.addGlobalError("O Campo Usuário é obrigatório");
				Messages.addGlobalError("O Campo Senha é obrigatório");
			} else {
				ProfessorDAO dao = new ProfessorDAO();
				dao.editarAcesso(usuario, senha, professor.getCod_pessoa());
				Messages.addGlobalInfo(Mensagem.MSG004);
				this.usuario = null;
				this.senha = null;
			}

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public void getRowDelete() {
		professor = professores.getRowData();
	}

	public void excluir() {

		try {
			ProfessorDAO dao = new ProfessorDAO();
			dao.excluir(professor);
			Messages.addGlobalInfo(Mensagem.MSG005);
			listar();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public void carregarDisciplinaProfessor() {
		try {
			professor = professores.getRowData();
			DisciplinaDAO dc = new DisciplinaDAO();
			disciplinas = dc.listarDisciplinas();
			ministrar = new Ministrar();
			MinistrarDAO dao = new MinistrarDAO();
			ArrayList<Ministrar> lista = dao.listar(professor.getCod_pessoa());
			ministrars = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	public void salvarDisciplinaProfessor() {
		try {
			MinistrarDAO dao = new MinistrarDAO();
			ministrar.setCodProfessor(professor.getCod_pessoa());
			if (ministrar.getAno() == null) {
				// a mensagem so esta aqui por que o campo aceita riquerid=true
				Messages.addGlobalError("O campo Ano é obrigatório");
				return;
			} else {
				String retorno = dao.salvar(ministrar);
				if (retorno.contains("sucesso")) {
					ArrayList<Ministrar> lista;
					lista = dao.listar(professor.getCod_pessoa());
					ministrars = new ListDataModel<>(lista);
				} else if (retorno.contains("professor_disciplina_pk")) {
					Messages.addGlobalWarn(Mensagem.MSG051);
				}
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRow() {
		ministrar = ministrars.getRowData();
	}

	public void excluirDC() {
		try {
			MinistrarDAO dao = new MinistrarDAO();
			boolean excluir = dao.verificarExclusao(ministrar.getCodProfessor(), ministrar.getCodDisciplina());
			if (excluir == true) {
				Messages.addGlobalWarn(Mensagem.MSG046);
			} else {
				dao.excluir(ministrar.getCodDisciplina(), ministrar.getCodProfessor());
				// Atualizar
				ArrayList<Ministrar> lista = dao.listar(professor.getCod_pessoa());
				ministrars = new ListDataModel<>(lista);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean isCPF(String CPF) {

		CPF = removeCaracteresEspeciais(CPF);

		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	private String removeCaracteresEspeciais(String doc) {
		if (doc.contains(".")) {
			doc = doc.replace(".", "");
		}
		if (doc.contains("-")) {
			doc = doc.replace("-", "");
		}
		if (doc.contains("/")) {
			doc = doc.replace("/", "");
		}
		return doc;
	}

	public void exibirform() {
		exibir = true;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public ListDataModel<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(ListDataModel<Professor> professores) {
		this.professores = professores;
	}

	public String getNomeprofessor() {
		return nomeprofessor;
	}

	public void setNomeprofessor(String nomeprofessor) {
		this.nomeprofessor = nomeprofessor;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
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

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Ministrar getMinistrar() {
		return ministrar;
	}

	public void setMinistrar(Ministrar ministrar) {
		this.ministrar = ministrar;
	}

	public ListDataModel<Ministrar> getMinistrars() {
		return ministrars;
	}

	public void setMinistrars(ListDataModel<Ministrar> ministrars) {
		this.ministrars = ministrars;
	}

}
