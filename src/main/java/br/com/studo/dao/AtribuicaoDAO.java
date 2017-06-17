package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Atividade;
import br.com.studo.domain.Atribuicao;
import br.com.studo.domain.Disciplina;
import br.com.studo.domain.Resposta;
import br.com.studo.domain.Turma;
import br.com.studo.util.Conexao;

public class AtribuicaoDAO {

	// retorna a quantidade de questões que uma atividade possui
	public int QtdQuestoes(Long codA) throws Exception {
		String sql = "SELECT COUNT(numero) FROM questao WHERE cod_atividade = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codA);
		ResultSet r = p.executeQuery();
		r.next();
		int quantidadeQuestoes = r.getInt(1);
		c.close();
		return quantidadeQuestoes;
	}

	public Long ultimaQuestao(Long codA) throws Exception {
		String sql = "SELECT MAX(cod_questao) from questao WHERE cod_atividade = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codA);
		ResultSet r = p.executeQuery();
		r.next();
		Long codUltmaQuestao = r.getLong(1);
		c.close();
		return codUltmaQuestao;

	}

	public String salvar(Atribuicao a) throws Exception {
		String retorno = "";
		try {
			Long codQuestao = ultimaQuestao(a.getCod_atividade());
			StringBuilder sql = new StringBuilder();
			sql.append(
					"INSERT INTO turma_atividade (cod_turma,cod_atividade,valor,date_resposta,situacao,qtd_questoes, cod_ultima_questao) ");
			sql.append("VALUES (?,?,?,?,?,?,?)");
			Connection c = Conexao.getConexao();
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setLong(1, a.getCod_turma());
			p.setLong(2, a.getCod_atividade());
			p.setDouble(3, a.getValor());
			p.setDate(4, a.getDate_resposta());
			p.setBoolean(5, a.getSituacao());
			p.setInt(6, a.getQtdQuestoes());
			p.setLong(7, codQuestao);
			p.execute();
			c.close();
			return retorno = "sucesso";
		} catch (Exception e) {
			return retorno + e.getMessage();
		}

	}

	public ArrayList<Atribuicao> listar(Long codAtividade) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT t.cod_turma, t.periodo, t.serie, t.turma, ta.date_resposta, ta.valor, ta.situacao FROM atividade a ");
		sql.append("INNER JOIN turma_atividade ta ON a.cod_atividade=ta.cod_atividade ");
		sql.append("INNER JOIN turma t ON ta.cod_turma =t.cod_turma ");
		sql.append("WHERE a.cod_atividade = ? ORDER BY ta.date_resposta ASC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codAtividade);
		ResultSet r = p.executeQuery();
		ArrayList<Atribuicao> lista = new ArrayList<>();
		while (r.next()) {
			Atribuicao at = new Atribuicao();
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setPeriodo(r.getString(2));
			t.setSerie(r.getString(3));
			t.setTurma(r.getString(4));
			at.setDate_resposta(r.getDate(5));
			at.setValor(r.getDouble(6));
			at.setSituacao(r.getBoolean(7));
			at.setTurma(t);
			lista.add(at);
		}
		c.close();
		return lista;

	}

	public void editar(Atribuicao a) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE turma_atividade SET valor = ?, date_resposta = ?, situacao= ?");
		sql.append("WHERE cod_turma = ? AND cod_atividade = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setDouble(1, a.getValor());
		p.setDate(2, a.getDate_resposta());
		p.setBoolean(3, a.getSituacao());
		p.setLong(4, a.getTurma().getCod_turma());
		p.setLong(5, a.getCod_atividade());
		p.executeUpdate();
		c.close();
	}

	public ArrayList<Resposta> recuperarRespostaAluno(Long codT, Long codA) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.cod_questao, r.matricula FROM resposta r ");
		sql.append("INNER JOIN aluno a ON r.matricula = a.matricula ");
		sql.append("INNER JOIN turma_aluno ta ON a.matricula = ta.matricula ");
		sql.append("INNER JOIN questao q ON r.cod_questao = q.cod_questao ");
		sql.append("INNER JOIN atividade at ON q.cod_atividade = at.cod_atividade ");
		sql.append("WHERE ta.cod_turma = ? AND at.cod_atividade = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codT);
		p.setLong(2, codA);
		ResultSet r = p.executeQuery();
		ArrayList<Resposta> lista = new ArrayList<>();
		while (r.next()) {
			Resposta resposta = new Resposta();
			resposta.setCod_questao(r.getLong(1));
			resposta.setMatricula(r.getLong(2));
			lista.add(resposta);
		}
		c.close();
		return lista;
	}

	public void excluir(Atribuicao a) throws Exception {
		Connection c = null;
		try {
			c = Conexao.getConexao();
			c.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM turma_atividade ");
			sql.append("WHERE cod_turma = ?  AND cod_atividade = ?");
			PreparedStatement p = c.prepareStatement(sql.toString());
			p.setLong(1, a.getTurma().getCod_turma());
			p.setLong(2, a.getCod_atividade());
			p.execute();
			// exclui as respostas dos alunos
			ArrayList<Resposta> lista = recuperarRespostaAluno(a.getTurma().getCod_turma(), a.getCod_atividade());
			for (Resposta r : lista) {
				System.out.println(r.getCod_questao() + " - " + r.getMatricula());
				String sql1 = "DELETE FROM resposta WHERE cod_questao = ? AND matricula = ?";
				PreparedStatement ps = c.prepareStatement(sql1.toString());
				ps.setLong(1, r.getCod_questao());
				ps.setLong(2, r.getMatricula());
				ps.execute();
			}
			c.commit();
			c.close();
		} catch (Exception e) {
			c.rollback();
			e.printStackTrace();
		}

	}

	// Carrega os detalhes da atividade e da disciplina
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

	// FILTRO DE PESQUISA
	// metodo retorna todas as series de um periodo
	public ArrayList<String> litarSeries(String periodo, String ano) throws Exception {
		String sql = "SELECT DISTINCT serie FROM turma WHERE periodo = ? AND ano = ? GROUP BY serie";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, periodo);
		p.setString(2, ano);
		ResultSet r = p.executeQuery();
		ArrayList<String> lista = new ArrayList<>();
		while (r.next()) {
			String serie = new String();
			serie = r.getString(1);
			lista.add(serie);
		}
		c.close();
		return lista;
	}

	// metódo retonas as tumas
	public ArrayList<Turma> listarTurmas(String periodo, String serie, String ano) throws Exception {
		String sql = "	SELECT cod_turma, turma FROM turma WHERE periodo = ? AND serie = ? AND ano = ? ";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, periodo);
		p.setString(2, serie);
		p.setString(3, ano);
		ResultSet r = p.executeQuery();
		ArrayList<Turma> lista = new ArrayList<>();
		while (r.next()) {
			Turma t = new Turma();
			t.setCod_turma(r.getLong(1));
			t.setTurma(r.getString(2));
			lista.add(t);
		}
		c.close();
		return lista;
	}

}
