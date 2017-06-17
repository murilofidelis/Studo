package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Test;

import br.com.studo.dao.RelatorioDAO;
import br.com.studo.domain.Relatorio;

public class RelatorioTeste {

	@Test
	public void teste() {
		try {
			RelatorioDAO dao = new RelatorioDAO();
			ArrayList<Relatorio> lista = dao.listar(5L, 1L);
			for (Relatorio r : lista) {
				System.out.println(r.getMatricula());
				System.out.println(r.getNome());
				System.out.println(r.getQtd());
				System.out.println(r.getNota());
				System.out.println("____________________");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
