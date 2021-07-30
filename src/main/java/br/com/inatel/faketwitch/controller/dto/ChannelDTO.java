package br.com.inatel.faketwitch.controller.dto;

import br.com.inatel.faketwitch.modelo.Channel;

public class ChannelDTO extends SimplifiedChannelDTO {

	private Integer nLiveStreams;
	private Integer nInConnections;
	private Integer nOutConnections;
	private Integer nCategories;

	public ChannelDTO(Channel channel) {
		super(channel);
		this.nLiveStreams = channel.getStreams().size();
		this.nInConnections = channel.getInConnections().size();
		this.nOutConnections = channel.getOutConnections().size();
		this.nCategories = channel.getCategories().size();
	}

	public Integer getnLiveStreams() {
		return nLiveStreams;
	}

	public Integer getnInConnections() {
		return nInConnections;
	}

	public Integer getnOutConnections() {
		return nOutConnections;
	}

	public Integer getnCategories() {
		return nCategories;
	}

}
