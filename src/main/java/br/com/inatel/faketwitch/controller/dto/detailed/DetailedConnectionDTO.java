package br.com.inatel.faketwitch.controller.dto.detailed;

import br.com.inatel.faketwitch.controller.dto.ChannelDTO;
import br.com.inatel.faketwitch.controller.dto.ConnectionDTO;
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
