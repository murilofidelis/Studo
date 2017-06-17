package br.com.studo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Atividade;
import br.com.studo.domain.Disciplina;
import br.com.studo.util.Conexao;

public class AtividadeDAO {

	public void salvar(Atividade a, int codProfessor) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO atividade ");
			sql.append("(cod_disciplina,data_criacao,titulo,descricao,classificacao,cod_professor) ");
			sql.append("VALUES (?,?,?,?,?,?)");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setInt(1, a.getDisciplina().getCod_disciplina());
			p.setDate(2, a.getData_criacao());
			p.setString(3, a.getTitulo());
			p.setString(4, a.getDescricao());
			p.setString(5, a.getClassificacao());
			p.setInt(6, codProfessor);
			p.execute();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public ArrayList<Atividade> litsarAtividade(int codProfessor) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT d.*, a.* FROM atividade a ");
		sql.append("INNER JOIN disciplina d ON ");
		sql.append("a.cod_disciplina = d.cod_disciplina ");
		sql.append("WHERE a.cod_professor = ? ORDER BY a.data_criacao DESC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, codProfessor);
		ResultSet r = p.executeQuery();
		ArrayList<Atividade> lista = new ArrayList<>();
		while (r.next()) {
			Atividade a = new Atividade();
			Disciplina d = new Disciplina();
			d.setCod_disciplina(r.getInt(1));
			d.setDescricao(r.getString(2));
			a.setCod_atividade(r.getLong(3));
			a.setData_criacao(r.getDate(5));
			a.setTitulo(r.getString(6));
			a.setDescricao(r.getString(7));
			a.setClassificacao(r.getString(8));
			a.setDisciplina(d);
			lista.add(a);
		}
		c.close();
		return lista;

	}

	// filtro de pesquisa por data
	public ArrayList<Atividade> listarAtividade(int codProfessor, Date de, Date ate) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT d.*, a.* FROM atividade a ");
		sql.append("INNER JOIN disciplina d ON ");
		sql.append("a.cod_disciplina = d.cod_disciplina ");
		sql.append("WHERE a.cod_professor = ? AND a.data_criacao  ");
		sql.append("BETWEEN ? AND ? ORDER BY a.data_criacao DESC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, codProfessor);
		p.setDate(2, de);
		p.setDate(3, ate);
		ResultSet r = p.executeQuery();
		ArrayList<Atividade> lista = new ArrayList<>();
		while (r.next()) {
			Atividade a = new Atividade();
			Disciplina d = new Disciplina();
			d.setCod_disciplina(r.getInt(1));
			d.setDescricao(r.getString(2));
			a.setCod_atividade(r.getLong(3));
			a.setData_criacao(r.getDate(5));
			a.setTitulo(r.getString(6));
			a.setDescricao(r.getString(7));
			a.setClassificacao(r.getString(8));

			a.setDisciplina(d);
			lista.add(a);
		}
		c.close();
		return lista;

	}

	public void editar(Atividade a) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE atividade SET cod_disciplina = ?, titulo = ?, descricao = ?, classificacao = ? ");
		sql.append("WHERE cod_atividade = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, a.getDisciplina().getCod_disciplina());
		p.setString(2, a.getTitulo());
		p.setString(3, a.getDescricao());
		p.setString(4, a.getClassificacao());
		p.setLong(5, a.getCod_atividade());
		p.executeUpdate();
		c.close();
	}

	public void excluir(Atividade a) throws Exception {
		String sql = "DELETE FROM atividade WHERE cod_atividade = ? ";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, a.getCod_atividade());
		p.execute();
		p.close();
	}

}
