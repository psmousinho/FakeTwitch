package br.com.inatel.faketwitch.controller.dto;

import br.com.inatel.faketwitch.modelo.Connection;

public class DetailedConnectionDTO extends ConnectionDTO {

	private ChannelDTO owner;

	public DetailedConnectionDTO(Connection connection) {
		super(connection);
		this.owner = new ChannelDTO(connection.getOwner());
	}

	public ChannelDTO getOwner() {
		return owner;
	}

}
