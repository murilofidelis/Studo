package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Test;

import br.com.studo.dao.AtribuicaoDAO;
import br.com.studo.domain.Resposta;

public class AtribuicaoTeste {

	@Test
	public void teste() {
		try {
			AtribuicaoDAO dao = new AtribuicaoDAO();

			ArrayList<Resposta> lista = new ArrayList<>();
			lista = dao.recuperarRespostaAluno(1L, 5L);
			for (Resposta r : lista) {
				System.out.println(r.getCod_questao() + " - " + r.getMatricula());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
