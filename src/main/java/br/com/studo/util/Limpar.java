package br.com.studo.util;

import br.com.studo.domain.Questao;

public class Limpar {

	public static void limparCampos(Questao q) {

		q.setNumero(null);
		q.setAlt_correta("");
		q.setDescricao("");
		q.setAlt_a("");
		q.setAlt_b("");
		q.setAlt_c("");
		q.setAlt_d("");
		q.setAlt_e("");
	}

	public static void limparAlternativas(Questao q) {

		q.setAlt_correta("");
		q.setAlt_a("");
		q.setAlt_b("");
		q.setAlt_c("");
		q.setAlt_d("");
		q.setAlt_e("");
	}

}
