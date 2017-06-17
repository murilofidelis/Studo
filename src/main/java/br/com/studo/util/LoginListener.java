package br.com.studo.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.studo.controller.LoginController;
import br.com.studo.domain.Pessoa;

@SuppressWarnings("serial")
public class LoginListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();
		boolean isLogin = paginaAtual.contains("login.xhtml");

		if (!isLogin) {
			LoginController loginController = Faces.getSessionAttribute("loginController");
			if (loginController == null) {
				Faces.navigate("/paginas/login.xhtml");
				return;
			}
			Pessoa pessoa = loginController.getPessoaLogada();
			if (pessoa == null) {
				Faces.navigate("/paginas/login.xhtml");
				return;
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
