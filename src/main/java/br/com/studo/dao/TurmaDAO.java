package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.studo.domain.Turma;
import br.com.studo.util.Conexao;
import br.com.studo.util.DataUtil;

public class TurmaDAO {

	public String salvar(Turma t) {
		String retorno = "";
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO turma ");
			sql.append("(serie, turma, sala, periodo, ano) ");
			sql.append("VALUES (?,?,?,?,?)");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setString(1, t.getSerie());
			p.setString(2, t.getTurma().toUpperCase());
			p.setString(3, t.getSala());
			p.setString(4, t.getPeriodo());
			p.setString(5, t.getAno());
			p.execute();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			return retorno + e.getMessage();
		}

	}

	public ArrayList<Turma> listarTurma() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM turma ");
		sql.append(" WHERE ano = ? ");
		sql.append("ORDER BY periodo ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, DataUtil.anoAtual());
		ResultSet r = p.executeQuery();
		ArrayList<Turma> lista = new ArrayList<>();
		while (r.next()) {
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setSerie(r.getString(2));
			t.setTurma(r.getString(3));
			t.setSala(r.getString(4));
			t.setPeriodo(r.getString(5));
			t.setAno(r.getString(6));
			lista.add(t);
		}
		c.close();
		return lista;
	}

	public String editarTurma(Turma t) throws Exception {
		String retorno = "";
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE turma ");
			sql.append("SET serie = ?, turma = ?, sala = ?, periodo = ?, ano = ? ");
			sql.append("WHERE cod_turma = ? ");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setString(1, t.getSerie());
			p.setString(2, t.getTurma().toUpperCase());
			p.setString(3, t.getSala());
			p.setString(4, t.getPeriodo());
			p.setString(5, t.getAno());
			p.setLong(6, t.getCod_turma());
			p.executeUpdate();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			return retorno + e.getMessage();
		}
	}

	public void excluir(Turma t) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM turma ");
		sql.append("WHERE cod_turma = ?  ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, t.getCod_turma());
		p.execute();
		c.close();
	}

	// FILTRO POR ANO
	public ArrayList<Turma> filtar(String ano) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM turma ");
		sql.append("WHERE ano = ? ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, ano);
		ResultSet r = p.executeQuery();
		ArrayList<Turma> lista = new ArrayList<>();
		while (r.next()) {
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setSerie(r.getString(2));
			t.setTurma(r.getString(3));
			t.setSala(r.getString(4));
			t.setPeriodo(r.getString(5));
			t.setAno(r.getString(6));
			lista.add(t);
		}
		c.close();
		return lista;

	}

	// FILTRO POR ANO E PERIODO
	public ArrayList<Turma> filtar(String ano, String periodo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM turma ");
		sql.append("WHERE ano = ? AND periodo = ? ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, ano);
		p.setString(2, periodo);
		ResultSet r = p.executeQuery();
		ArrayList<Turma> lista = new ArrayList<>();
		while (r.next()) {
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setSerie(r.getString(2));
			t.setTurma(r.getString(3));
			t.setSala(r.getString(4));
			t.setPeriodo(r.getString(5));
			t.setAno(r.getString(6));

			lista.add(t);
		}
		c.close();
		return lista;
	}

	// Recumpera uma turma para a tela de cadastro do aluno.
	public Turma buscarTurmar(Long cod) throws Exception {
		Calendar dt = Calendar.getInstance();
		int ano = dt.get(Calendar.YEAR);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM turma ");
		sql.append("WHERE cod_turma = ? AND ano = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, cod);
		p.setString(2, String.valueOf(ano));
		ResultSet r = p.executeQuery();
		Turma t = null;
		while (r.next()) {
			t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setSerie(r.getString(2));
			t.setTurma(r.getString(3));
			t.setSala(r.getString(4));
			t.setPeriodo(r.getString(5));
			t.setAno(r.getString(6));
		}
		return t;
	}

}
