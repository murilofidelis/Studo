package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Atividade;
import br.com.studo.domain.Disciplina;
import br.com.studo.domain.Questao;
import br.com.studo.util.Conexao;

public class QuestaoDAO {

	public ArrayList<Questao> listar(Long codAtividade) throws Exception {
		String sql = "SELECT * FROM questao WHERE cod_atividade = ? ORDER BY numero ASC ";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codAtividade);
		ResultSet r = p.executeQuery();
		ArrayList<Questao> lista = new ArrayList<>();
		while (r.next()) {
			Questao q = new Questao();
			q.setCod_questao(r.getLong(1));
			q.setCod_atividade(r.getLong(2));
			q.setNumero(r.getInt(3));
			q.setDescricao(r.getString(4));
			q.setTipo(r.getInt(5));
			q.setAlt_correta(r.getString(6));
			q.setAlt_a(r.getString(7));
			q.setAlt_b(r.getString(8));
			q.setAlt_c(r.getString(9));
			q.setAlt_d(r.getString(10));
			q.setAlt_e(r.getString(11));
			lista.add(q);
		}
		c.close();
		return lista;
	}

	public void salvar(Questao q) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO questao ");
		sql.append("(cod_atividade,numero,descricao,tipo,alt_correta,alt_a,alt_b,alt_c,alt_d,alt_e) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?)");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, q.getCod_atividade());
		p.setInt(2, q.getNumero());
		p.setString(3, q.getDescricao());
		p.setInt(4, q.getTipo());
		p.setString(5, q.getAlt_correta().toUpperCase());
		p.setString(6, q.getAlt_a());
		p.setString(7, q.getAlt_b());
		p.setString(8, q.getAlt_c());
		p.setString(9, q.getAlt_d());
		p.setString(10, q.getAlt_e());
		p.execute();
		c.close();
	}

	public Atividade detalharAtividade(Long codAtividade) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.titulo,d.descricao FROM atividade a ");
		sql.append("INNER JOIN disciplina d ON a.cod_disciplina = d.cod_disciplina ");
		sql.append(" WHERE a.cod_atividade = ? ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codAtividade);
		ResultSet r = p.executeQuery();
		Atividade a = new Atividade();
		while (r.next()) {
			Disciplina d = new Disciplina();
			a.setTitulo(r.getString(1));
			d.setDescricao(r.getString(2));
			a.setDisciplina(d);
		}
		c.close();
		return a;
	}

	public void editar(Questao q) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE questao SET ");
		sql.append(
				"numero = ?, descricao = ?, tipo = ?, alt_correta = ?, alt_a = ?, alt_b = ?, alt_c = ?, alt_d = ?, alt_e = ? ");
		sql.append("WHERE cod_questao =  ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, q.getNumero());
		p.setString(2, q.getDescricao());
		p.setInt(3, q.getTipo());
		p.setString(4, q.getAlt_correta().toUpperCase());
		p.setString(5, q.getAlt_a());
		p.setString(6, q.getAlt_b());
		p.setString(7, q.getAlt_c());
		p.setString(8, q.getAlt_d());
		p.setString(9, q.getAlt_e());
		p.setLong(10, q.getCod_questao());
		p.executeUpdate();
		c.close();

	}

	public void excluir(Questao q) throws Exception {
		String sql = "DELETE FROM questao WHERE cod_questao = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, q.getCod_questao());
		p.execute();
		c.close();
	}

	public Boolean verificar(Long cod) throws Exception {
		String sql = "SELECT cod_turma FROM turma_atividade WHERE cod_atividade = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, cod);
		ResultSet r = p.executeQuery();
		boolean existeTurmaRespondendo;
		if (r.next()) {
			existeTurmaRespondendo = true;
		} else {
			existeTurmaRespondendo = false;
		}
		return existeTurmaRespondendo;

	}

}
