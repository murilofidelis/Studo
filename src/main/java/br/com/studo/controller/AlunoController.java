package br.com.studo.controller;

import java.sql.Date;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.studo.dao.AlunoDAO;
import br.com.studo.dao.TurmaDAO;
import br.com.studo.domain.Aluno;
import br.com.studo.domain.Turma;
import br.com.studo.util.DataUtil;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class AlunoController {

	private Long matricula;
	private String nome;
	private String ano;
	private String periodo;
	private String serie;
	private String turma;

	private ListDataModel<Aluno> alunos;
	private ArrayList<Aluno> selecionados;
	private Aluno aluno;
	private Turma t;
	private ListDataModel<Turma> turmasAluno;
	private Long codTurmaPesquisada;
	private int qtdAunosSelecionados;
	// so serve para pegar a data(String) da tela.
	private String dtNascimento;

	public void pesquisar() {
		try {
			if (matricula == null && nome.equals("") && ano.equals("") && periodo.equals("") && serie.equals("")
					&& turma.equals("")) {
				Messages.addGlobalWarn(Mensagem.MSG029);
			} else {

				if (nome.equals("")) {
					nome = null;
				}
				if (ano.equals("")) {
					ano = null;
				}
				if (periodo.equals("")) {
					periodo = null;
				}
				if (serie.equals("")) {
					serie = null;
				}
				if (turma.equals("")) {
					turma = null;
				}
				AlunoDAO dao = new AlunoDAO();
				ArrayList<Aluno> lista = new ArrayList<>();
				lista = dao.pesquisar(matricula, nome, ano, periodo, serie, turma);
				alunos = new ListDataModel<>(lista);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}

	}

	public void novo() {
		aluno = new Aluno();
		this.dtNascimento = null;
		this.codTurmaPesquisada = null;
		t = null;
	}

	public void recumperaTurma() {
		try {
			TurmaDAO dao = new TurmaDAO();
			t = dao.buscarTurmar(codTurmaPesquisada);
			if (t == null) {
				Messages.addGlobalWarn(Mensagem.MSG047);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (t == null) {
				Messages.addGlobalWarn(Mensagem.MSG047);
				return;
			}
			AlunoDAO dao = new AlunoDAO();
			Date dtnascimento = DataUtil.formatDate(dtNascimento);
			aluno.setData_nascimento(dtnascimento);
			aluno.setSituacao(true);
			aluno.setTurma(t);
			String retorno = dao.salvar(aluno);
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG030);
				novo();
				RequestContext.getCurrentInstance().execute("PF('dlgCadastro').hide();");
			} else if (retorno.contains("aluno_pk")) {
				Messages.addGlobalWarn(Mensagem.MSG031);
			} else {
				Messages.addGlobalError(retorno);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public void carregarAlocacao() {
		qtdAunosSelecionados = selecionados.size();
		this.codTurmaPesquisada = null;
		t = null;
	}

	public void salvarAlocacao() {
		try {
			if (t == null) {
				Messages.addGlobalWarn(Mensagem.MSG047);
				return;
			}
			AlunoDAO dao = new AlunoDAO();
			boolean pendencia = false;
			boolean exibeMSN = false;
			// verificar se algum aluno selecionado ja esta matriculado na turma
			for (Aluno a : selecionados) {
				boolean jaMatriculado = dao.verificarAlunoTurma(t.getCod_turma(), a.getMatricula());
				if (jaMatriculado == true) {
					pendencia = true;
					Messages.addGlobalWarn("O aluno " + a.getNome() + " já está matriculado na turma: "
							+ t.getCod_turma() + " - " + t.getSerie() + " - " + t.getTurma());
				}
			}
			for (Aluno s : selecionados) {
				if (pendencia == false) {
					dao.salvarAlocacao(t.getCod_turma(), s.getMatricula());
					exibeMSN = true;
				}
			}
			if (exibeMSN == true) {
				Messages.addGlobalInfo(Mensagem.MSG048);
				RequestContext.getCurrentInstance().execute("PF('dlgAlocar').hide();");
				pesquisar();
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void carregarEdicao() {
		try {
			aluno = alunos.getRowData();
			dtNascimento = DataUtil.formatDateForString(aluno.getData_nascimento());
			this.codTurmaPesquisada = null;
			t = null;
			AlunoDAO dao = new AlunoDAO();
			ArrayList<Turma> lista = dao.listarTurmasAluno(aluno.getMatricula());
			turmasAluno = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}
	}

	public void editar() {
		try {
			AlunoDAO dao = new AlunoDAO();
			Date data = DataUtil.formatDate(dtNascimento);
			aluno.setData_nascimento(data);
			dao.editar(aluno);
			Messages.addGlobalInfo(Mensagem.MSG004);
			pesquisar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// carrega a turma selecionada na lista de turma
	public void getRowTurmaDel() {
		t = turmasAluno.getRowData();
	}

	public void excluiTurma() {
		try {
			int i = turmasAluno.getRowCount();
			if (i == 1) {
				Messages.addGlobalWarn(Mensagem.MSG049);
				return;
			} else {
				AlunoDAO dao = new AlunoDAO();
				dao.excluiTurma(t.getCod_turma(), aluno.getMatricula());
				ArrayList<Turma> lista = dao.listarTurmasAluno(aluno.getMatricula());
				turmasAluno = new ListDataModel<>(lista);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getAlunoDel() {
		aluno = alunos.getRowData();
	}

	public void excluir() {
		try {
			AlunoDAO dao = new AlunoDAO();
			dao.excluir(aluno.getMatricula());
			Messages.addGlobalInfo(Mensagem.MSG028);
			pesquisar();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	String usuario;
	String senha;

	public void editarAcesso() {
		if (usuario.equals("") && !senha.equals("")) {
			Messages.addGlobalError("O Campo Usuário é obrigatório");
		} else if (!usuario.equals("") && senha.equals("")) {
			Messages.addGlobalError("O Campo Senha é obrigatório");
		} else if (usuario.equals("") || senha.equals("")) {
			Messages.addGlobalError("O Campo Usuário é obrigatório");
			Messages.addGlobalError("O Campo Senha é obrigatório");
		} else {
			AlunoDAO dao = new AlunoDAO();
			String retorno = dao.editarAcesso(usuario, senha, aluno.getMatricula());
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG004);
				this.usuario = null;
				this.senha = null;
			} else if (retorno.contains("usuario_senha_alunounico")) {
				Messages.addGlobalWarn(Mensagem.MSG040);
				this.usuario = null;
				this.senha = null;
			} else {
				Messages.addGlobalError(retorno);
			}
		}
	}

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

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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

	public ListDataModel<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(ListDataModel<Aluno> alunos) {
		this.alunos = alunos;
	}

	public ArrayList<Aluno> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(ArrayList<Aluno> selecionados) {
		this.selecionados = selecionados;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void setT(Turma t) {
		this.t = t;
	}

	public Turma getT() {
		return t;
	}

	public Long getCodTurmaPesquisada() {
		return codTurmaPesquisada;
	}

	public void setCodTurmaPesquisada(Long codTurmaPesquisada) {
		this.codTurmaPesquisada = codTurmaPesquisada;
	}

	public int getQtdAunosSelecionados() {
		return qtdAunosSelecionados;
	}

	public void setQtdAunosSelecionados(int qtdAunosSelecionados) {
		this.qtdAunosSelecionados = qtdAunosSelecionados;
	}

	public String getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public ListDataModel<Turma> getTurmasAluno() {
		return turmasAluno;
	}

	public void setTurmasAluno(ListDataModel<Turma> turmasAluno) {
		this.turmasAluno = turmasAluno;
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
