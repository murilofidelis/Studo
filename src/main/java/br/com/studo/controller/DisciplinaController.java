package br.com.studo.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.omnifaces.util.Messages;

import br.com.studo.dao.DisciplinaDAO;
import br.com.studo.domain.Disciplina;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class DisciplinaController {

	private Disciplina disciplina;
	private ListDataModel<Disciplina> disciplinas;

	@PostConstruct
	public void listar() {
		try {
			DisciplinaDAO dao = new DisciplinaDAO();
			ArrayList<Disciplina> lista;
			lista = dao.listarDisciplinas();
			disciplinas = new ListDataModel<>(lista);
		} catch (Exception e) {
			Messages.addGlobalInfo(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public void novo() {
		disciplina = new Disciplina();
	}

	public void salvar() {
		try {
			DisciplinaDAO dao = new DisciplinaDAO();
			String retorno = dao.salvar(disciplina);
			if (retorno.contains("sucesso")) {
				Messages.addGlobalInfo(Mensagem.MSG003);
				listar();
			} else if (retorno.contains("disciplina_unico")) {
				Messages.addGlobalWarn(Mensagem.MSG007);
			}

		} catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
		}
	}

	public void getRowExcluir() {
		disciplina = disciplinas.getRowData();
	}

	public void excluir() {
		try {
			DisciplinaDAO dao = new DisciplinaDAO();
			dao.excluir(disciplina);
			ArrayList<Disciplina> lista = dao.listarDisciplinas();
			disciplinas = new ListDataModel<>(lista);
			Messages.addGlobalInfo(Mensagem.MSG005);

		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
			e.printStackTrace();
		}

	}

	public void getRowEditar() {
		disciplina = disciplinas.getRowData();
	}

	public void editar() {
		try {
			DisciplinaDAO dao = new DisciplinaDAO();
			dao.editar(disciplina);
			Messages.addGlobalInfo(Mensagem.MSG004);
			listar();
		} catch (Exception e) {
			Messages.addGlobalWarn(e.getMessage());
			e.printStackTrace();
		}
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public ListDataModel<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(ListDataModel<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
