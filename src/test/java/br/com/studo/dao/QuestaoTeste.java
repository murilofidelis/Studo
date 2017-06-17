package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import br.com.studo.dao.QuestaoDAO;
import br.com.studo.domain.Atividade;
import br.com.studo.domain.Questao;

public class QuestaoTeste {
	@Test
	@Ignore
	public void listar() {

		try {
			QuestaoDAO dao = new QuestaoDAO();
			ArrayList<Questao> lista = new ArrayList<>();
			lista = dao.listar(5L);
			for (Questao q : lista) {
				System.out.println(q.getCod_questao());
				System.out.println(q.getDescricao());
				System.out.println(q.getAlt_a());
				System.out.println("__________________________");

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void detalhar() {

		try {
			QuestaoDAO dao = new QuestaoDAO();
			Atividade a = new Atividade();
			a = dao.detalharAtividade(5L);
			System.out.println(a.getDescricao());
			System.out.println(a.getDisciplina().getDescricao());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
