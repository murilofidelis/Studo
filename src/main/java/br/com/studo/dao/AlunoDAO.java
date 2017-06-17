package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Aluno;
import br.com.studo.domain.Turma;
import br.com.studo.util.Conexao;
import br.com.studo.util.DataUtil;
import br.com.studo.util.Filtro;

public class AlunoDAO extends Filtro {

	public ArrayList<Aluno> pesquisar(Long matricula, String nome, String ano, String periodo, String serie,
			String turma) throws Exception {
		boolean filtroIniciado = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.*,t.* FROM aluno a ");
		sql.append("INNER JOIN turma_aluno ta ON a.matricula = ta.matricula ");
		sql.append("INNER JOIN turma t ON ta.cod_turma =  t.cod_turma WHERE ");
		if (matricula != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("a.matricula = " + matricula);
			filtroIniciado = true;
		}
		if (nome != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("a.nome = '" + nome + "'");
			filtroIniciado = true;
		}
		if (ano != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("t.ano = '" + ano + "'");
			filtroIniciado = true;
		}
		if (periodo != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("t.periodo = '" + periodo + "'");
			filtroIniciado = true;
		}
		if (serie != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("t.serie = '" + serie + "'");
			filtroIniciado = true;
		}
		if (turma != null) {
			adicionarAndSql(filtroIniciado, sql);
			sql.append("t.turma = '" + turma.toUpperCase() + "'");
			filtroIniciado = true;
		}
		sql.append("AND ta.atual = true");
		System.out.println(sql.toString());
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		ResultSet r = p.executeQuery();
		ArrayList<Aluno> lista = new ArrayList<>();
		while (r.next()) {
			Aluno al = new Aluno();
			Turma t = new Turma();
			al.setMatricula(r.getLong(1));
			al.setNome(r.getString(2));
			al.setEmail(r.getString(3));
			al.setSexo(r.getString(4));
			al.setSituacao(r.getBoolean(5));
			al.setData_nascimento(r.getDate(6));
			al.setUsuario(r.getString(7));
			al.setSenha(r.getString(8));
			t.setCod_turma(r.getLong(9));
			t.setSerie(r.getString(10));
			t.setTurma(r.getString(11));
			t.setSala(r.getString(12));
			t.setPeriodo(r.getString(13));
			t.setAno(r.getString(14));
			al.setTurma(t);
			lista.add(al);
		}
		c.close();
		return lista;
	}

	public String salvar(Aluno a) throws Exception {
		String retorno = "";
		Connection c = Conexao.getConexao();
		c.setAutoCommit(false);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO aluno ");
			sql.append("(matricula,nome,email,sexo,situacao,data_nascimento,usuario,senha) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?)");
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setLong(1, a.getMatricula());
			p.setString(2, a.getNome());
			p.setString(3, a.getEmail());
			p.setString(4, a.getSexo());
			p.setBoolean(5, a.getSituacao());
			p.setDate(6, a.getData_nascimento());
			p.setString(7, a.getUsuario());
			p.setString(8, a.getSenha());
			p.execute();
			StringBuilder sql1 = new StringBuilder();
			sql1.append("INSERT INTO turma_aluno ");
			sql1.append("(cod_turma,matricula,data_matricula,atual) ");
			sql1.append("VALUES (?,?,current_timestamp,true)");
			PreparedStatement ps = c.prepareStatement(sql1.toString());
			ps.setLong(1, a.getTurma().getCod_turma());
			ps.setLong(2, a.getMatricula());
			ps.execute();
			c.commit();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			c.rollback();
			return retorno + e.getMessage();
		}
	}

	// altera a turma atual para FALSE, Depois aloca o aluno na nova turma e
	// "seta" a nova turma como TRUE
	public void salvarAlocacao(Long codTurma, Long matricula) throws Exception {
		Connection c = Conexao.getConexao();
		c.setAutoCommit(false);
		try {
			StringBuilder sqlUpdate = new StringBuilder();
			sqlUpdate.append("UPDATE turma_aluno SET atual = false ");
			sqlUpdate.append("WHERE matricula = ?");
			PreparedStatement ps = c.prepareStatement(sqlUpdate.toString());
			ps.setLong(1, matricula);
			ps.execute();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO turma_aluno ");
			sql.append("(cod_turma,matricula,data_matricula,atual) ");
			sql.append("VALUES (?,?,current_timestamp,true)");
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setLong(1, codTurma);
			p.setLong(2, matricula);
			p.execute();
			c.commit();
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			c.close();
		}
	}

	// Esse método serve para verificar se o aluno esta matriculado em alguma
	// tumar
	// (retorna TRUE caso o aluno já esteja na turma)
	public boolean verificarAlunoTurma(Long codTurma, Long matricula) throws Exception {
		Boolean existe = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  t.cod_turma FROM ");
		sql.append("turma t INNER JOIN turma_aluno ta ");
		sql.append("ON t.cod_turma = ta.cod_turma ");
		sql.append("INNER JOIN aluno a ");
		sql.append("ON ta.matricula =  a.matricula ");
		sql.append("WHERE t.cod_turma = ? AND a.matricula = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codTurma);
		p.setLong(2, matricula);
		ResultSet r = p.executeQuery();
		c.close();
		if (r.next()) {
			existe = true;
		} else {
			existe = false;
		}
		return existe;
	}

	public ArrayList<Turma> listarTurmasAluno(Long matricula) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  t.*,ta.data_matricula FROM turma t ");
		sql.append("INNER JOIN turma_aluno ta ON ");
		sql.append("t.cod_turma = ta.cod_turma ");
		sql.append("INNER JOIN aluno a ON ");
		sql.append("ta.matricula =  a.matricula ");
		sql.append("WHERE a.matricula = ? ");
		sql.append("ORDER BY ta.data_matricula DESC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, matricula);
		ResultSet r = p.executeQuery();
		ArrayList<Turma> lista = new ArrayList<>();
		while (r.next()) {
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setSerie(r.getString(2));
			t.setTurma(r.getString(3));
			t.setSala(r.getString(4));
			t.setPeriodo(r.getString(5));
			t.setAno(DataUtil.formatDateForString(r.getDate(7)));
			lista.add(t);
		}
		c.close();
		return lista;
	}

	public void editar(Aluno a) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE aluno ");
		sql.append("SET nome = ?, email = ?, sexo = ?, situacao = ?, data_nascimento = ? ");
		sql.append("WHERE matricula = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, a.getNome());
		p.setString(2, a.getEmail());
		p.setString(3, a.getSexo());
		p.setBoolean(4, a.getSituacao());
		p.setDate(5, a.getData_nascimento());
		p.setLong(6, a.getMatricula());
		p.executeUpdate();
		c.close();
	}

	// Desvincular o aluno da turma. apos exluir a turma, a turma anterior onde
	// aluno estava matricula passa a ser principal.
	public void excluiTurma(Long codTurma, Long mat) throws Exception {
		Connection c = Conexao.getConexao();
		c.setAutoCommit(false);
		try {
			StringBuilder sql1 = new StringBuilder();
			sql1.append("DELETE FROM turma_aluno  ");
			sql1.append("WHERE matricula = ? AND cod_turma = ? ");
			PreparedStatement ps1 = c.prepareStatement(sql1.toString());
			ps1.setLong(1, mat);
			ps1.setLong(2, codTurma);
			ps1.execute();
			StringBuilder sql2 = new StringBuilder();
			sql2.append("UPDATE turma_aluno SET atual = true ");
			sql2.append("WHERE matricula = ? AND data_matricula = (SELECT MAX(data_matricula) from turma_aluno)");
			PreparedStatement ps2 = c.prepareStatement(sql2.toString());
			ps2.setLong(1, mat);
			ps2.execute();
			c.commit();
		} catch (Exception e) {
			c.rollback();
		} finally {
			c.close();
		}
	}

	public void excluir(Long matricula) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM aluno ");
		sql.append("WHERE matricula = ?  ");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, matricula);
		p.execute();
		c.close();
	}

	public String editarAcesso(String usuario, String senha, Long matricula) {
		String retorno = "";
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE aluno ");
			sql.append("SET usuario = ?, senha = ? ");
			sql.append("WHERE matricula = ? ");
			Connection c = Conexao.getConexao();
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setString(1, usuario);
			ps.setString(2, senha);
			ps.setLong(3, matricula);
			ps.executeUpdate();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			e.printStackTrace();
			return retorno + e.getMessage();
		}
	}

}
