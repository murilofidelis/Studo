package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Disciplina;
import br.com.studo.util.Conexao;

public class DisciplinaDAO {

	public String salvar(Disciplina d) {
		String retorno;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO disciplina ");
			sql.append("(descricao) ");
			sql.append("VALUES (?)");

			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setString(1, d.getDescricao());
			p.execute();
			c.close();
			retorno = "sucesso";
		} catch (Exception e) {
			retorno = e.getMessage();

		}
		return retorno;

	}

	public ArrayList<Disciplina> listarDisciplinas() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append("FROM disciplina ");
		sql.append("ORDER BY descricao ASC");

		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		ResultSet r = p.executeQuery();
		ArrayList<Disciplina> lista = new ArrayList<>();
		while (r.next()) {
			Disciplina d = new Disciplina();
			d.setCod_disciplina(r.getInt(1));
			d.setDescricao(r.getString(2));
			lista.add(d);
		}
		c.close();
		return lista;

	}

	public void excluir(Disciplina d) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM disciplina ");
		sql.append("WHERE cod_disciplina = ? ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, d.getCod_disciplina());
		p.executeUpdate();
		c.close();

	}

	public void editar(Disciplina d) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE disciplina ");
		sql.append("SET descricao = ? ");
		sql.append(" WHERE cod_disciplina = ? ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, d.getDescricao());
		p.setInt(2, d.getCod_disciplina());
		p.executeUpdate();
		c.close();
	}

	// metodo usdo para listar as discilinas do professor autenticado na hora de
	// criar uma tividade.
	public ArrayList<Disciplina> listarDisciplinasProfessor(int codProLogado) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT d.* FROM disciplina d ");
		sql.append("INNER JOIN professor_disciplina pd ON ");
		sql.append("d.cod_disciplina = pd.cod_disciplina ");
		sql.append("WHERE pd.cod_pessoa = ? ");
		sql.append("ORDER BY descricao ASC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codProLogado);
		ResultSet r = p.executeQuery();
		ArrayList<Disciplina> lista = new ArrayList<>();
		while (r.next()) {
			Disciplina d = new Disciplina();
			d.setCod_disciplina(r.getInt(1));
			d.setDescricao(r.getString(2));
			lista.add(d);
		}
		c.close();
		return lista;

	}

}
