package br.com.studo.dao;

import org.junit.Ignore;
import org.junit.Test;

import br.com.studo.dao.DisciplinaDAO;
import br.com.studo.domain.Disciplina;

public class DisciplinaTeste {

	@Test
	@Ignore
	public void salvar() {
		try {
			Disciplina disciplina = new Disciplina();
			DisciplinaDAO dao = new DisciplinaDAO();
			disciplina.setDescricao(("Biológia").toUpperCase());
			dao.salvar(disciplina);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void excluir() {
		DisciplinaDAO d = new DisciplinaDAO();
		Disciplina disciplina = new Disciplina();
		disciplina.setCod_disciplina(7);
		try {
			d.excluir(disciplina);
			System.out.println("excluido");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
