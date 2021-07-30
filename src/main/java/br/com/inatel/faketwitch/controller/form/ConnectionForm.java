package br.com.inatel.faketwitch.controller.form;

import javax.validation.constraints.NotNull;

import br.com.inatel.faketwitch.modelo.ConnectionStatus;

public class ConnectionForm {

	@NotNull
	private ConnectionStatus status;

	public void setStatus(ConnectionStatus status) {
		this.status = status;
	}

	public ConnectionStatus getStatus() {
		return status;
	}

}
