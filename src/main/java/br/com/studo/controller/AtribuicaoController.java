package br.com.studo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.studo.dao.AtribuicaoDAO;
import br.com.studo.domain.Atividade;
import br.com.studo.domain.Atribuicao;
import br.com.studo.domain.Turma;
import br.com.studo.util.DataUtil;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class AtribuicaoController {

	private String periodo;
	private String serie;
	private ArrayList<String> series;
	private ArrayList<Turma> tumas;
	private AtribuicaoDAO dao;
	private Atribuicao atribuicao;
	private ListDataModel<Atribuicao> atribuicoes;
	private Long codAtividade;
	private Long codTurma;
	private Atividade atividade;

	@PostConstruct
	public void carregarDetalhes() {
		try {
			codAtividade = Long.parseLong(System.getProperty("codAtividadeParaAtribuicao"));
			dao = new AtribuicaoDAO();
			atividade = dao.detalharAtividade(codAtividade);
			listarAtribuicoes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarAtribuicoes() {
		try {
			ArrayList<Atribuicao> lista = dao.listar(codAtividade);
			atribuicoes = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}
	}

	Date dtMinima;

	public void novo() {
		periodo = null;
		serie = null;
		series = null;
		tumas = null;
		dataReposta = null;
		atribuicao = new Atribuicao();
		dtMinima = DataUtil.dataAtual();
	}

	Date dataReposta;

	public void salvar() {
		try {
			atribuicao.setDate_resposta(DataUtil.formatDateForSQL(dataReposta));
			atribuicao.setCod_atividade(codAtividade);
			atribuicao.setSituacao(false);
			atribuicao.setQtdQuestoes(dao.QtdQuestoes(codAtividade));
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, -1);
			cal1.setTime(atribuicao.getDate_resposta());
			if (cal1.before(cal2)) {
				Messages.addGlobalWarn(Mensagem.MSG025);
				return;
			}
			String retorno = dao.salvar(atribuicao);
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG020);
				RequestContext.getCurrentInstance().execute("PF('dlg').hide();");
				listarAtribuicoes();
			} else if (retorno.contains("turma_atividade_pk")) {
				Messages.addGlobalWarn(Mensagem.MSG053);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRowEdit() {
		atribuicao = atribuicoes.getRowData();
		dataReposta = DataUtil.formatUtilDate(atribuicao.getDate_resposta());
		dtMinima = DataUtil.dataAtual();

	}

	public void editar() {
		try {
			atribuicao.setCod_atividade(codAtividade);
			atribuicao.setDate_resposta(DataUtil.formatDateForSQL(dataReposta));
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.DATE, -1);
			cal1.setTime(atribuicao.getDate_resposta());
			if (cal1.before(cal2)) {
				Messages.addGlobalWarn(Mensagem.MSG025);
				return;
			}
			dao.editar(atribuicao);
			Messages.addGlobalInfo(Mensagem.MSG022);
			RequestContext.getCurrentInstance().execute("PF('dlgEdicao').hide();");
			listarAtribuicoes();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRowDel() {
		atribuicao = atribuicoes.getRowData();
	}

	public void excluir() {
		try {
			atribuicao.setCod_atividade(codAtividade);
			dao.excluir(atribuicao);
			Messages.addGlobalInfo(Mensagem.MSG024);
			listarAtribuicoes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarSeries() {
		try {
			series = dao.litarSeries(periodo, DataUtil.anoAtual());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarTurmas() {
		try {
			tumas = dao.listarTurmas(periodo, serie, DataUtil.anoAtual());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// trasfere o código da atividade e da turma para a tela de relatório.
	public void trasferir() {
		try {
			atribuicao = atribuicoes.getRowData();
			System.setProperty("codAtividade", String.valueOf(codAtividade));
			System.setProperty("codTurma", String.valueOf(atribuicao.getTurma().getCod_turma()));
			Faces.redirect("./paginas/pro/relatorio.xhtml");
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public Long getCodTurma() {
		return codTurma;
	}

	public void setCodTurma(Long codTurma) {
		this.codTurma = codTurma;
	}

	public ArrayList<String> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<String> series) {
		this.series = series;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Atribuicao getAtribuicao() {
		return atribuicao;
	}

	public void setAtribuicao(Atribuicao atribuicao) {
		this.atribuicao = atribuicao;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public ArrayList<Turma> getTumas() {
		return tumas;
	}

	public void setTumas(ArrayList<Turma> tumas) {
		this.tumas = tumas;
	}

	public Date getDataReposta() {
		return dataReposta;
	}

	public void setDataReposta(Date dataReposta) {
		this.dataReposta = dataReposta;
	}

	public Long getCodAtividade() {
		return codAtividade;
	}

	public void setCodAtividade(Long codAtividade) {
		this.codAtividade = codAtividade;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public ListDataModel<Atribuicao> getAtribuicoes() {
		return atribuicoes;
	}

	public void setAtribuicoes(ListDataModel<Atribuicao> atribuicoes) {
		this.atribuicoes = atribuicoes;
	}

	public Date getDtMinima() {
		return dtMinima;
	}

	public void setDtMinima(Date dtMinima) {
		this.dtMinima = dtMinima;
	}

}
