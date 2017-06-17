package br.com.studo.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.studo.dao.ConfiguracaoDAO;
import br.com.studo.util.Mensagem;

@ManagedBean
@ViewScoped
public class ConfiguracaoController {

	private int cod;
	private ConfiguracaoDAO dao;

	@PostConstruct
	public void carrgar() {
		try {
			dao = new ConfiguracaoDAO();
			cod = dao.buscar();
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}
	}

	public void salvar() {
		try {
			dao.salvar(cod);
			Messages.addGlobalInfo(Mensagem.MSG036);
		} catch (Exception e) {
			Messages.addGlobalError(Mensagem.ERRO + e.getMessage());
		}

	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

}
