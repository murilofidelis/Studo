package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.studo.domain.Pessoa;
import br.com.studo.util.Conexao;

public class LoginDAO {

	public Pessoa autenticar(String user, String password) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pessoa ");
		sql.append("WHERE usuario = ? AND senha = ? AND situacao = true");
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setString(1, user);
		p.setString(2, password);
		ResultSet r = p.executeQuery();
		Pessoa pessoa = null;
		while (r.next()) {
			pessoa = new Pessoa();
			pessoa.setExiste(true);
			pessoa.setCod_pessoa(r.getInt(1));
			pessoa.setCod_tipo(r.getInt(2));
			pessoa.setNome(r.getString(3));
			pessoa.setCpf(r.getString(4));
			pessoa.setSituacao(r.getBoolean(5));
			pessoa.setEmail(r.getString(6));
			pessoa.setTitulo(r.getString(7));
			pessoa.setSexo(r.getString(8));
			pessoa.setUsuario(r.getString(9));
			pessoa.setSenha(r.getString(10));
		}
		c.close();
		if (pessoa == null) {
			pessoa = new Pessoa();
			pessoa.setExiste(false);
			return pessoa;
		} else {
			return pessoa;
		}
	}

	public void alterar(Pessoa p) throws Exception {
		String sql = "UPDATE pessoa SET nome = ?, email = ? WHERE cod_pessoa = ? ";
		Connection c = Conexao.getConexao();
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setString(1, p.getNome());
		ps.setString(2, p.getEmail());
		ps.setLong(3, p.getCod_pessoa());
		ps.execute();
		c.close();
	}

	public String alterarAcesso(Pessoa p) {
		String retorno = "";
		try {

			String sql = "UPDATE pessoa SET usuario = ?, senha = ? WHERE cod_pessoa = ? ";
			Connection c = Conexao.getConexao();
			PreparedStatement ps = c.prepareStatement(sql.toString());
			ps.setString(1, p.getUsuario());
			ps.setString(2, p.getSenha());
			ps.setLong(3, p.getCod_pessoa());
			ps.execute();
			c.close();
			retorno = "sucesso";
			return retorno;
		} catch (Exception e) {
			return retorno + e.getMessage();
		}

	}

}
