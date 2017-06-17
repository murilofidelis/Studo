package br.com.studo.util;

public class Filtro {

	protected void adicionarAndSql(Boolean iniciado, StringBuilder sql) {
		if (iniciado) {
			sql.append(" AND ");
		} else {
			iniciado = true;
		}
	}

}
