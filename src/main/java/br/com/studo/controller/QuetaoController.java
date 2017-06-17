package br.com.studo.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.studo.dao.QuestaoDAO;
import br.com.studo.domain.Atividade;
import br.com.studo.domain.Questao;
import br.com.studo.util.Limpar;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class QuetaoController {

	private ListDataModel<Questao> questoes;
	private QuestaoDAO dao;
	private Atividade atividade;
	private Long codAtividade;
	private Questao questao;
	private String acao;
	private String info;
	private boolean podeExcluir;

	@PostConstruct
	public void listarQuestoes() {
		try {
			codAtividade = Long.parseLong(System.getProperty("codAtividadeParaAtribuicao"));
			dao = new QuestaoDAO();
			ArrayList<Questao> lista = dao.listar(codAtividade);
			questoes = new ListDataModel<>(lista);
			atividade = dao.detalharAtividade(codAtividade);
			if (lista.size() == 0) {
				info = Mensagem.MSG019;
			} else {
				info = "";
				podeExcluir = dao.verificar(codAtividade);
				System.out.println("pode: "+podeExcluir);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	String txt;

	public void novo() {
		questao = new Questao();
		txt = "Nova Questão";
		acao = "novo";
	}

	public void salvar() {
		try {
			if (acao.equals("novo")) {
				questao.setCod_atividade(codAtividade);
				dao.salvar(questao);
				Messages.addGlobalInfo(Mensagem.MSG015);
				Limpar.limparCampos(questao);
			} else if (acao.equals("edicao")) {
				dao.editar(questao);
				Messages.addGlobalInfo(Mensagem.MSG016);
				RequestContext.getCurrentInstance().execute("PF('dlgCadastro').hide();");
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	boolean exibir1 = false;
	boolean exibir2 = false;

	public void exibir() {
		if (questao.getTipo() == 1) {
			exibir1 = true;
			exibir2 = false;

		} else if (questao.getTipo() == 2) {
			exibir1 = false;
			exibir2 = true;
			Limpar.limparAlternativas(questao);
		}

	}

	public void getRowEdit() {
		questao = questoes.getRowData();
		txt = "Editar Questão";
		acao = "edicao";
		if (questao.getTipo() == 1) {
			exibir1 = true;
			exibir2 = false;
		} else if (questao.getTipo() == 2) {
			exibir1 = false;
			exibir2 = true;

		}

	}

	public void getRowDel() {
		questao = questoes.getRowData();
	}

	public void excluir() {
		try {
			dao.excluir(questao);
			Messages.addGlobalInfo(Mensagem.MSG018);
			listarQuestoes();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public ListDataModel<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(ListDataModel<Questao> questoes) {
		this.questoes = questoes;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public void setCodAtividade(Long codAtividade) {
		this.codAtividade = codAtividade;
	}

	public Long getCodAtividade() {
		return codAtividade;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public boolean isExibir1() {
		return exibir1;
	}

	public void setExibir1(boolean exibir1) {
		this.exibir1 = exibir1;
	}

	public boolean isExibir2() {
		return exibir2;
	}

	public void setExibir2(boolean exibir2) {
		this.exibir2 = exibir2;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isPodeExcluir() {
		return podeExcluir;
	}

	public void setPodeExcluir(boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}

}
