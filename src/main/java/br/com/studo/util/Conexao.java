package br.com.studo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static Connection conexao;

	// BD_teste = studo_teste
	// BD produção = STUDO

	/*private static final String URL_CONEXAO = "jdbc:postgresql://localhost:5432/STUDO";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "123456";*/
	
	
	private static final String URL_CONEXAO = "jdbc:postgres://uhfnnqaklyktqt:f4199fc1f00b17cee937d0169efe1670182ba1b626e4e42994d7b2281af6271e@ec2-54-197-232-155.compute-1.amazonaws.com:5432/d61lt994e6cods";
	private static final String USUARIO = "uhfnnqaklyktqt";
	private static final String SENHA = "f4199fc1f00b17cee937d0169efe1670182ba1b626e4e42994d7b2281af6271e";

	public static Connection getConexao() throws ClassNotFoundException, SQLException {

		Class.forName("org.postgresql.Driver");
		conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
		return conexao;
	}

}