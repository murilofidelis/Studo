package br.com.studo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.studo.dao.AtividadeDAO;
import br.com.studo.dao.DisciplinaDAO;
import br.com.studo.domain.Atividade;
import br.com.studo.domain.Disciplina;
import br.com.studo.util.DataUtil;
import br.com.studo.util.Mensagem;
import br.com.studo.util.UsuarioLogUtil;

@ManagedBean
@ViewScoped
public class AtividadeController {

	int codProfessor = UsuarioLogUtil.codUsuario();
	private Atividade atividade;
	private ArrayList<Disciplina> comboDisciplinas;
	private ListDataModel<Atividade> atividades;
	AtividadeDAO dao = new AtividadeDAO();
	DisciplinaDAO d = new DisciplinaDAO();
	String acao = null;

	@PostConstruct
	public void listar() {
		try {
			ArrayList<Atividade> lista = dao.litsarAtividade(codProfessor);
			atividades = new ListDataModel<>(lista);
			acao = "listar";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// filtros de data
	java.util.Date de;
	java.util.Date ate;

	public void buscar() {
		try {
			if (de == null || ate == null) {
				Messages.addGlobalError("O campo Periodo é obrigatórios");
				return;
			}
			ArrayList<Atividade> lista = dao.listarAtividade(codProfessor, DataUtil.formatDateForSQL(de),
					DataUtil.formatDateForSQL(ate));
			atividades = new ListDataModel<>(lista);
			acao = "buscar";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void novo() {
		try {
			atividade = new Atividade();
			comboDisciplinas = d.listarDisciplinasProfessor(codProfessor);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}
	}

	public void salvar() {
		try {
			atividade.setData_criacao(DataUtil.dataAtualFormatada());
			dao.salvar(atividade, codProfessor);
			listar();
			Messages.addGlobalInfo(Mensagem.MSG011);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRowEdit() {
		try {
			atividade = atividades.getRowData();
			comboDisciplinas = d.listarDisciplinasProfessor(codProfessor);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void editar() {
		try {
			dao.editar(atividade);
			Messages.addGlobalInfo(Mensagem.MSG012);

			if (acao.equals("buscar")) {
				buscar();
			} else if (acao.equals("listar")) {
				listar();
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRowDelete() {
		atividade = atividades.getRowData();
	}

	public void excluir() {
		try {
			dao.excluir(atividade);
			Messages.addGlobalInfo(Mensagem.MSG014);
			listar();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void atribuirAtividade() {
		try {
			atividade = atividades.getRowData();
			System.setProperty("codAtividadeParaAtribuicao", String.valueOf(atividade.getCod_atividade()));
			Faces.redirect("./paginas/pro/atribuicao.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void listarQuestoes() {
		try {
			atividade = atividades.getRowData();
			System.setProperty("codAtividadeParaAtribuicao", String.valueOf(atividade.getCod_atividade()));
			Faces.redirect("./paginas/pro/questao.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public ArrayList<Disciplina> getComboDisciplinas() {
		return comboDisciplinas;
	}

	public void setComboDisciplinas(ArrayList<Disciplina> comboDisciplinas) {
		this.comboDisciplinas = comboDisciplinas;
	}

	public ListDataModel<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(ListDataModel<Atividade> atividades) {
		this.atividades = atividades;
	}

	public java.util.Date getDe() {
		return de;
	}

	public void setDe(java.util.Date de) {
		this.de = de;
	}

	public java.util.Date getAte() {
		return ate;
	}

	public void setAte(java.util.Date ate) {
		this.ate = ate;
	}

}
