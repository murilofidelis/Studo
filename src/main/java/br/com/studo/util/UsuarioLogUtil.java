package br.com.studo.util;

import org.omnifaces.util.Faces;

import br.com.studo.controller.LoginController;
import br.com.studo.domain.Pessoa;

public class UsuarioLogUtil {

	// retona as infomações do usuario logado.
	public static Pessoa usuarioLogado() {
		LoginController loginController = Faces.getSessionAttribute("loginController");
		Pessoa pessoa = loginController.getPessoaLogada();
		System.out.println(pessoa.getCod_pessoa());
		System.out.println(pessoa.getNome());
		return pessoa;
	}

	// retonas apenas o codigo do usuáriomlogado
	public static Integer codUsuario() {
		Integer cod;
		LoginController loginController = Faces.getSessionAttribute("loginController");
		Pessoa pessoa = loginController.getPessoaLogada();
		cod = pessoa.getCod_pessoa();
		System.out.println("código usuário logado: "+cod);
		return cod;

	}

}
