package br.com.studo.dao;

import org.junit.Test;

import br.com.studo.dao.LoginDAO;
import br.com.studo.domain.Pessoa;

public class LoginTeste {

	@Test
	public void login() {

		try {
			LoginDAO dao = new LoginDAO();

			Pessoa p = dao.autenticar("m", "m");

			if (p.isExiste() == false) {

				System.out.println("Ususario ou senha incorretos");
			} else {

				System.out.println(p.getNome());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
