package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.studo.domain.Professor;
import br.com.studo.util.Conexao;

public class ProfessorDAO {

	public void salvar(Professor p) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO pessoa ");
			sql.append("(cod_tipo,nome,cpf,situacao,email,sexo,titulo,usuario,senha) ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?)");
			Connection c = Conexao.getConexao();
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setInt(1, p.getCod_tipo());
			ps.setString(2, p.getNome());
			ps.setString(3, p.getCpf());
			ps.setBoolean(4, p.getSituacao());
			ps.setString(5, p.getEmail());
			ps.setString(6, p.getSexo());
			ps.setString(7, p.getTitulo());
			ps.setString(8, p.getUsuario());
			ps.setString(9, p.getSenha());
			ps.execute();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ArrayList<Professor> listarProfessor() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pessoa ");
		sql.append("WHERE cod_tipo = 2 AND situacao = TRUE ORDER BY nome");
		Connection c = Conexao.getConexao();
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet r = ps.executeQuery();
		ArrayList<Professor> lista = new ArrayList<>();
		while (r.next()) {
			Professor p = new Professor();
			p.setCod_pessoa(r.getInt(1));
			p.setNome(r.getString(3));
			p.setCpf(r.getString(4));
			p.setSituacao(r.getBoolean(5));
			p.setEmail(r.getString(6));
			p.setTitulo(r.getString(7));
			p.setSexo(r.getString(8));
			lista.add(p);
		}
		c.close();
		return lista;

	}

	public ArrayList<Professor> listarPorNome(String nome) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pessoa ");
		sql.append("WHERE nome LIKE ? AND cod_tipo = 2 ORDER BY nome");
		Connection c = Conexao.getConexao();
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setString(1, '%' + nome + '%');
		ResultSet r = ps.executeQuery();
		ArrayList<Professor> lista = new ArrayList<>();
		while (r.next()) {
			Professor p = new Professor();
			p.setCod_pessoa(r.getInt(1));
			p.setNome(r.getString(3));
			p.setCpf(r.getString(4));
			p.setSituacao(r.getBoolean(5));
			p.setEmail(r.getString(6));
			p.setTitulo(r.getString(7));
			p.setSexo(r.getString(8));
			lista.add(p);
		}
		c.close();
		return lista;

	}

	public void editar(Professor p) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE pessoa ");
			sql.append("SET nome = ?, cpf = ?, situacao = ?, email = ?, titulo = ?, sexo = ? ");
			sql.append("WHERE cod_pessoa = ? ");
			Connection c = Conexao.getConexao();
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setString(1, p.getNome());
			ps.setString(2, p.getCpf());
			ps.setBoolean(3, p.getSituacao());
			ps.setString(4, p.getEmail());
			ps.setString(5, p.getTitulo());
			ps.setString(6, p.getSexo());
			ps.setInt(7, p.getCod_pessoa());
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void editarAcesso(String usuario, String senha, int cod) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE pessoa ");
			sql.append("SET usuario = ?, senha = ? ");
			sql.append("WHERE cod_pessoa = ? ");
			Connection c = Conexao.getConexao();
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setString(1, usuario);
			ps.setString(2, senha);
			ps.setInt(3, cod);
			ps.executeUpdate();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void excluir(Professor p) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM pessoa ");
		sql.append("WHERE cod_pessoa = ?  ");
		Connection c = Conexao.getConexao();
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, p.getCod_pessoa());
		ps.execute();
	}

	public ArrayList<String> verificarCPFInsert() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cpf from pessoa");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		ResultSet r = p.executeQuery();
		ArrayList<String> cpfs = new ArrayList<>();
		while (r.next()) {
			String cpf = new String();
			cpf = r.getString(1);
			cpfs.add(cpf);
		}
		c.close();
		return cpfs;
	}

}
