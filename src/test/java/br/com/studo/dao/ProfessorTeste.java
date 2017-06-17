package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import br.com.studo.dao.MinistrarDAO;
import br.com.studo.dao.ProfessorDAO;
import br.com.studo.domain.Ministrar;
import br.com.studo.domain.Professor;

public class ProfessorTeste {

	@Test
	@Ignore
	public void salvar() {
		try {

			Professor p = new Professor();
			ProfessorDAO dao = new ProfessorDAO();

			p.setCod_tipo(2);
			p.setNome("Murilo");
			p.setCpf("035.206.841-06");
			p.setSituacao(true);
			p.setEmail("murilo@teste.com");
			p.setSexo("M");
			p.setTitulo("DR");
			p.setUsuario("teste");
			p.setSenha("teste");
			dao.salvar(p);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void listar() {
		ProfessorDAO dao = new ProfessorDAO();

		try {
			ArrayList<Professor> lista;
			lista = dao.listarPorNome("Mu");

			for (Professor p : lista) {
				System.out.println(p.getCod_pessoa());
				System.out.println(p.getNome());
				System.out.println(p.getCpf());
				System.out.println(p.getSituacao());
				System.out.println(p.getEmail());
				System.out.println(p.getTitulo());
				System.out.println(p.getSexo());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	// @Ignore
	public void listarDisciplina() {
		try {

			MinistrarDAO dao = new MinistrarDAO();
			ArrayList<Ministrar> lista = new ArrayList<>();
			lista = dao.listar(2);
			for (Ministrar m : lista) {
				System.out.println(m.getCodDisciplina());
				System.out.println(m.getCodProfessor());
				System.out.println(m.getDescDisciplina());
				System.out.println(m.getAno());
				System.out.println("_________");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
