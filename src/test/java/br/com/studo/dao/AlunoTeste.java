package br.com.studo.dao;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import br.com.studo.dao.AlunoDAO;
import br.com.studo.domain.Aluno;
import br.com.studo.domain.Turma;
import br.com.studo.util.DataUtil;

public class AlunoTeste extends DataUtil {
	@Test
	@Ignore
	public void teste() {
		try {
			AlunoDAO dao = new AlunoDAO();

			ArrayList<Aluno> list = new ArrayList<>();

			// list = dao.pesquisar(null, "Aluno 1", "2016");
			list = dao.pesquisar(null, null, null, null, "8º Série", "A");
			// list = dao.pesquisar(12346, null);
			for (Aluno a : list) {

				System.out.println(a.getNome());
				System.out.println(a.getTurma().getCod_turma());
				System.out.println(a.getTurma().getSerie() + " " + a.getTurma().getTurma());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void save() {
		try {
			AlunoDAO dao = new AlunoDAO();
			Aluno a = new Aluno();
			Turma t = new Turma();
			a.setMatricula(22122L);
			a.setNome("TESTE");
			a.setEmail("aluno5@teste.com");
			a.setSexo("M");
			a.setSituacao(true);
			java.sql.Date d = formatDate("10-10-2016");
			a.setData_nascimento(d);
			a.setUsuario("aluno5");
			a.setSenha("alu5");
			t.setCod_turma(2L);
			a.setTurma(t);

			dao.salvar(a);
			System.out.println("salvo!");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@Test
	public void verificar() {

		try {
			AlunoDAO d = new AlunoDAO();

			boolean ex = d.verificarAlunoTurma(3L, 12345L);

			if (ex == false) {
				System.out.println("aluno não cadastrado na turma");

			} else if (ex == true) {
				System.out.println("aluno cadastrado na turma");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
