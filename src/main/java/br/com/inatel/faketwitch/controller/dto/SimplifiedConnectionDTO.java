package br.com.inatel.faketwitch.controller.dto;

import java.time.LocalDate;

import br.com.inatel.faketwitch.modelo.Connection;
import br.com.inatel.faketwitch.modelo.ConnectionStatus;

public class SimplifiedConnectionDTO {

	protected Long id;
	protected LocalDate startDate;
	protected ConnectionStatus status;

	public SimplifiedConnectionDTO(Connection connection) {
		this.id = connection.getId();
		this.startDate = connection.getStartDate();
		this.status = connection.getStatus();
	}

	public Long getId() {
		return id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public ConnectionStatus getStatus() {
		return status;
	}
}
