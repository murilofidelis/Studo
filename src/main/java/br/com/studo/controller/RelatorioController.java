package br.com.studo.controller;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.servlet.ServletContext;

import org.omnifaces.util.Messages;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import br.com.studo.dao.RelatorioDAO;
import br.com.studo.domain.Atividade;
import br.com.studo.domain.Atribuicao;
import br.com.studo.domain.Relatorio;
import br.com.studo.domain.Turma;
import br.com.studo.util.DataUtil;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class RelatorioController {

	private Long CodAtividade;
	private Long CodTurma;
	private Atividade atividade;
	private Turma turma;
	private Atribuicao atribuicao;
	private int qtdQuestoes;
	private ListDataModel<Relatorio> relatorios;
	private ListDataModel<Relatorio> relatorioDetalhe;
	private Relatorio relatorio;
	ArrayList<Relatorio> lista;
	RelatorioDAO dao;

	@PostConstruct
	public void carregar() {
		try {
			dao = new RelatorioDAO();
			CodAtividade = Long.parseLong(System.getProperty("codAtividade"));
			CodTurma = Long.parseLong(System.getProperty("codTurma"));
			System.out.println(CodAtividade + " || " + CodTurma);
			atividade = dao.detalharAtividade(CodAtividade);
			turma = dao.detalharTurma(CodTurma);
			atribuicao = dao.deltalharAtirbuicao(CodAtividade, CodTurma);
			qtdQuestoes = dao.detalharQtdQuestoes(CodAtividade, CodTurma);
			lista = dao.listar(CodAtividade, CodTurma);
			relatorios = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void carreagrDetalhes() {
		try {
			relatorio = relatorios.getRowData();
			ArrayList<Relatorio> lista = dao.listaDetalhesL(relatorio.getMatricula(), CodAtividade);
			relatorioDetalhe = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}
	}

	public void preProcessPDF(Object document) throws Exception {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4);
		pdf.open();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String logo = servletContext.getRealPath("") + File.separator + "resources/studo/img" + File.separator
				+ "logo_nova.png";
		Image image = Image.getInstance(logo);
		image.setAlignment(Image.ALIGN_CENTER);
		// pragrafos
		Paragraph p = new Paragraph("Relatório da Atividade" + "\n",
				new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 204, 204)));
		p.setAlignment("center");
		Paragraph quebraDlinha = new Paragraph(" ");
		Paragraph p1 = new Paragraph("Atividade: " + atividade.getTitulo(),
				new Font(Font.HELVETICA, 12, Font.BOLD, new Color(64, 64, 64)));
		Paragraph p2 = new Paragraph(
				"Valor: " + atribuicao.getValor().toString() + "           Qtd. Questões: " + qtdQuestoes
						+ "           Responder até: " + DataUtil.formatDateForString(atribuicao.getDate_resposta()),
				new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(64, 64, 64)));
		Paragraph p3 = new Paragraph(
				turma.getSerie() + " - " + turma.getTurma() + " - " + turma.getPeriodo() + "\n" + " ",
				new Font(Font.HELVETICA, 12, Font.NORMAL, new Color(64, 64, 64)));
		p2.setAlignment("left");
		pdf.add(image);
		pdf.add(p);
		pdf.add(quebraDlinha);
		pdf.add(p1);
		pdf.add(p2);
		pdf.add(p3);
	}

	public ListDataModel<Relatorio> getRelatorioDetalhe() {
		return relatorioDetalhe;
	}

	public void setRelatorioDetalhe(ListDataModel<Relatorio> relatorioDetalhe) {
		this.relatorioDetalhe = relatorioDetalhe;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public ListDataModel<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(ListDataModel<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}

	public Long getCodAtividade() {
		return CodAtividade;
	}

	public void setCodAtividade(Long codAtividade) {
		CodAtividade = codAtividade;
	}

	public Long getCodTurma() {
		return CodTurma;
	}

	public void setCodTurma(Long codTurma) {
		CodTurma = codTurma;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Atribuicao getAtribuicao() {
		return atribuicao;
	}

	public void setAtribuicao(Atribuicao atribuicao) {
		this.atribuicao = atribuicao;
	}

	public int getQtdQuestoes() {
		return qtdQuestoes;
	}

	public void setQtdQuestoes(int qtdQuestoes) {
		this.qtdQuestoes = qtdQuestoes;
	}

}
