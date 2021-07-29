package br.com.inatel.faketwitch.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.inatel.faketwitch.modelo.ConnectionStatus;

public class CreateConnectionForm {
	
	@NotNull
	@NotEmpty
	private ConnectionStatus status;

	public void setStatus(ConnectionStatus status) {
		this.status = status;
	}
	
	

}
