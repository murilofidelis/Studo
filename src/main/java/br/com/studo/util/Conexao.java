package br.com.studo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static Connection conexao;

	// BD_teste = studo_teste
	// BD produção = STUDO

	private static final String URL_CONEXAO = "jdbc:postgresql://localhost:5432/STUDO";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "123456";

	public static Connection getConexao() throws ClassNotFoundException, SQLException {

		Class.forName("org.postgresql.Driver");
		conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
		return conexao;
	}

}