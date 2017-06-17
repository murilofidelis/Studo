package br.com.studo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.studo.util.Conexao;

public class ConfiguracaoDAO {

	public int buscar() throws Exception {
		String sql = "SELECT * FROM configuracao";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		ResultSet r = p.executeQuery();
		r.next();
		int cod = r.getInt(1);
		c.close();
		return cod;

	}

	public void salvar(int cod) throws Exception {
		String sql = "UPDATE configuracao set cod = ?";
		Connection c = Conexao.getConexao();
		PreparedStatement p = c.prepareStatement(sql.toString());
		p.setInt(1, cod);
		p.execute();
		c.close();
	}

}
