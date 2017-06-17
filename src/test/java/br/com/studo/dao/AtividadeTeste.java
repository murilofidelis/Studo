package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Test;

import br.com.studo.dao.AtividadeDAO;
import br.com.studo.domain.Atividade;

public class AtividadeTeste {
	@Test
	public void listar() {
		try {
			AtividadeDAO dao = new AtividadeDAO();
			ArrayList<Atividade> lista;

			lista = dao.litsarAtividade(2);

			for (Atividade a : lista) {

				System.out.println(a.getCod_atividade());
				System.out.println(a.getDisciplina().getDescricao());
				System.out.println(a.getData_criacao());

				System.out.println(a.getTitulo());
				System.out.println(a.getDescricao());

				System.out.println("__________________");

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
