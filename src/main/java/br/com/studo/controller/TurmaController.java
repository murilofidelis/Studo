package br.com.studo.controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.studo.dao.TurmaDAO;
import br.com.studo.domain.Turma;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class TurmaController {

	private ListDataModel<Turma> turmas;
	private Turma turma;
	private String ano;
	private String periodo;
	private boolean camp = true;

	@PostConstruct
	public void listar() {

		try {
			TurmaDAO dao = new TurmaDAO();
			ArrayList<Turma> lista;
			lista = dao.listarTurma();
			turmas = new ListDataModel<>(lista);

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public void novo() {
		turma = new Turma();
		Calendar dt = Calendar.getInstance();
		int ano = dt.get(Calendar.YEAR);
		turma.setAno(String.valueOf(ano));

	}

	public void salvar() {
		try {
			TurmaDAO dao = new TurmaDAO();
			String retorno = dao.salvar(turma);
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG003);
				RequestContext.getCurrentInstance().execute("PF('dlgCadastro').hide();");
				listar();
			} else if (retorno.contains("turma_unico")) {
				Messages.addGlobalWarn(Mensagem.MSG007);
			}

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void getRowEdit() {
		turma = turmas.getRowData();
	}

	public void editar() {
		try {
			TurmaDAO dao = new TurmaDAO();
			String retorno = dao.editarTurma(turma);
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG004);
				RequestContext.getCurrentInstance().execute("PF('dlgEdicao').hide();");
				listar();
			} else if (retorno.contains("turma_unico")) {
				Messages.addGlobalWarn(Mensagem.MSG007);
			}
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}

	}

	public void getRowDelet() {
		turma = turmas.getRowData();
	}

	public void excluir() {
		try {
			TurmaDAO dao = new TurmaDAO();
			dao.excluir(turma);
			Messages.addGlobalInfo(Mensagem.MSG005);
			listar();

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	// FILTRAR POR ANO E PERIODO
	public void buscarPorAnoPeriodo() {
		try {
			TurmaDAO dao = new TurmaDAO();
			ArrayList<Turma> lista = null;
			if (!ano.equals("") && periodo.equals("")) {
				lista = dao.filtar(ano);
			} else if (!ano.equals("") && !periodo.equals("")) {
				lista = dao.filtar(ano, periodo);
			} else if (ano.equals("") && periodo.equals("")) {
				listar();
				return;
			}
			turmas = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	// HABILITA O CAMPO PERIODO
	public void habilitar() {
		if (!ano.equals("")) {
			camp = false;
		} else {
			camp = true;
			periodo = "";
		}
	}

	public ListDataModel<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(ListDataModel<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
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

	public boolean isCamp() {
		return camp;
	}

	public void setCamp(boolean camp) {
		this.camp = camp;
	}

}
