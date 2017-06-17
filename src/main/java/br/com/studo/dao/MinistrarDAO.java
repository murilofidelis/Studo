package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Ministrar;
import br.com.studo.util.Conexao;

public class MinistrarDAO {

	public String salvar(Ministrar m) {
		String retorno = "";
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO professor_disciplina ");
			sql.append("(cod_disciplina, cod_pessoa, ano) ");
			sql.append("VALUES (?,?,?)");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setInt(1, m.getCodDisciplina());
			p.setInt(2, m.getCodProfessor());
			p.setInt(3, m.getAno());
			p.execute();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			return retorno + e.getMessage();
		}
	}

	public ArrayList<Ministrar> listar(int codProfessor) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT dp.cod_pessoa, dp.cod_disciplina, d.descricao, dp.ano ");
		sql.append("FROM disciplina d ");
		sql.append("INNER JOIN professor_disciplina dp ON ");
		sql.append("d.cod_disciplina = dp.cod_disciplina ");
		sql.append("INNER JOIN pessoa p ON ");
		sql.append("dp.cod_pessoa = p.cod_pessoa ");
		sql.append("WHERE p.cod_pessoa = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, codProfessor);
		ResultSet r = p.executeQuery();
		ArrayList<Ministrar> lista = new ArrayList<>();
		while (r.next()) {
			Ministrar m = new Ministrar();
			m.setCodProfessor(r.getInt(1));
			m.setCodDisciplina(r.getInt(2));
			m.setDescDisciplina(r.getString(3));
			m.setAno(r.getInt(4));
			lista.add(m);
		}
		c.close();
		return lista;
	}

	public String excluir(int codD, int codP) {
		String retorno = "";
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM professor_disciplina ");
			sql.append("WHERE cod_disciplina = ? AND cod_pessoa = ?");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setInt(1, codD);
			p.setInt(2, codP);
			p.execute();
			c.close();
			retorno = "sucesso";
			return retorno;
		} catch (Exception e) {
			return retorno + e.getMessage();

		}

	}

	public boolean verificarExclusao(int codP, int codD) throws Exception {
		String sql = "SELECT cod_atividade FROM atividade WHERE cod_professor = ? AND cod_disciplina = ? ";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codP);
		p.setLong(2, codD);
		ResultSet r = p.executeQuery();
		boolean permissao = r.next();
		System.out.println("PERMISSÂO: " + permissao);
		return permissao;

	}
}
