package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import br.com.studo.dao.TurmaDAO;
import br.com.studo.domain.Turma;

public class TurmaTeste {
	@Test
	// @Ignore
	public void salvar() {

		try {
			Turma t = new Turma();
			TurmaDAO dao = new TurmaDAO();

			t.setSerie("6serie");
			t.setPeriodo("Matutino");
			t.setTurma("B");
			t.setAno("2016");
			t.setSala("");

			dao.salvar(t);
			System.out.println("salvo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}

	}

	@Test
	@Ignore
	public void filtrar() {
		try {
			TurmaDAO dao = new TurmaDAO();

			ArrayList<Turma> lista = dao.filtar("2017");
			for (Turma t : lista) {
				System.out.println(t.getAno());
				System.out.println(t.getPeriodo());
				System.out.println(t.getSerie());
				System.out.println(t.getTurma());
				System.out.println(t.getSala());
				System.out.println("");

			}

		} catch (Exception e) {
		}
	}

}
