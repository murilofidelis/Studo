package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Atividade;
import br.com.studo.domain.Atribuicao;
import br.com.studo.domain.Relatorio;
import br.com.studo.domain.Turma;
import br.com.studo.util.Conexao;

public class RelatorioDAO {

	public Atividade detalharAtividade(Long cod) throws Exception {
		String sql = "SELECT titulo FROM atividade WHERE cod_atividade = ? ";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, cod);
		ResultSet r = p.executeQuery();
		r.next();
		Atividade at = new Atividade();
		at.setTitulo(r.getString(1));
		c.close();
		return at;
	}

	public Turma detalharTurma(Long cod) throws Exception {
		String sql = "SELECT periodo,serie,turma FROM turma WHERE cod_turma = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, cod);
		ResultSet r = p.executeQuery();
		r.next();
		Turma t = new Turma();
		t.setPeriodo(r.getString(1));
		t.setSerie(r.getString(2));
		t.setTurma(r.getString(3));
		c.close();
		return t;
	}

	public Atribuicao deltalharAtirbuicao(Long codA, Long codT) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT valor, date_resposta FROM turma_atividade ");
		sql.append("WHERE cod_atividade = ? AND cod_turma = ?");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codA);
		p.setLong(2, codT);
		ResultSet r = p.executeQuery();
		r.next();
		Atribuicao at = new Atribuicao();
		at.setValor(r.getDouble(1));
		at.setDate_resposta(r.getDate(2));
		c.close();
		return at;
	}

	// retorna a quantidade de questões atribuida para uma turma, no memento da
	// atribuição

	public int detalharQtdQuestoes(Long codA, Long codT) throws Exception {
		String sql = "SELECT qtd_questoes FROM turma_atividade WHERE cod_atividade = ? AND cod_turma = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codA);
		p.setLong(2, codT);
		ResultSet r = p.executeQuery();
		r.next();
		int quantidadeQuestoes = r.getInt(1);
		c.close();
		return quantidadeQuestoes;
	}

	public ArrayList<Relatorio> listar(Long codA, Long codT) throws Exception {
		ArrayList<Relatorio> lista = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.matricula, a.nome, SUM (nota), SUM(r.acerto) FROM aluno a ");
		sql.append("INNER JOIN turma_aluno ta on a.matricula = ta.matricula ");
		sql.append("INNER JOIN resposta r ON a.matricula =  r.matricula ");
		sql.append("INNER JOIN questao q ON r.cod_questao = q.cod_questao ");
		sql.append("INNER JOIN atividade at ON q.cod_atividade = at.cod_atividade ");
		sql.append("WHERE at.cod_atividade = ? AND  ta.cod_turma = ? GROUP BY a.matricula");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, codA);
		p.setLong(2, codT);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			Relatorio re = new Relatorio();
			re.setMatricula(r.getLong(1));
			re.setNome(r.getString(2));
			re.setNota(r.getDouble(3));
			re.setQtd(r.getInt(4));
			lista.add(re);
		}
		c.close();
		return lista;
	}

	public ArrayList<Relatorio> listaDetalhesL(Long matricula, Long codAtividade) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT q.numero, q.descricao, q.alt_correta, r.resposta, r.data_resposta ");
		sql.append("FROM questao q INNER JOIN resposta r ON q.cod_questao = r.cod_questao ");
		sql.append("INNER JOIN aluno a ON r.matricula = a.matricula ");
		sql.append("WHERE a.matricula = ? AND q.cod_atividade= ? ORDER BY q.numero ASC");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setLong(1, matricula);
		p.setLong(2, codAtividade);
		ResultSet r = p.executeQuery();
		ArrayList<Relatorio> lista = new ArrayList<>();
		while (r.next()) {
			Relatorio re = new Relatorio();
			re.setNumero(r.getInt(1));
			re.setDecricaoQuestao(r.getString(2));
			re.setAltCorreta(r.getString(3));
			re.setAltEscolhida(r.getString(4));
			re.setDataResposta(r.getDate(5));
			lista.add(re);
		}
		c.close();
		return lista;

	}

}
