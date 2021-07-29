package br.com.inatel.faketwitch.controller.dto.simplified;

import br.com.inatel.faketwitch.modelo.Channel;

public class SimplifiedChannelDTO {

	private Long id;
	private String name;

	public SimplifiedChannelDTO(Channel channel) {
		this.id = channel.getId();
		this.name = channel.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
