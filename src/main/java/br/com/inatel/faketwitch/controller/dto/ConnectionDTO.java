package br.com.inatel.faketwitch.controller.dto;

import br.com.inatel.faketwitch.controller.dto.simplified.SimplifiedConnectionDTO;
import br.com.inatel.faketwitch.modelo.Connection;

public class ConnectionDTO extends SimplifiedConnectionDTO {

	protected ChannelDTO target;

	public ConnectionDTO(Connection connection) {
		super(connection);
		this.target = new ChannelDTO(connection.getTarget());
	}

	public ChannelDTO getTarget() {
		return target;
	}

}
