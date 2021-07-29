package br.com.inatel.faketwitch.controller.dto.detailed;

import java.util.List;

import br.com.inatel.faketwitch.controller.dto.ChannelDTO;
import br.com.inatel.faketwitch.modelo.Channel;

public class DetailedChannelDTO extends ChannelDTO {

	private List<DetailedConnectionDTO> inConnections;
	private List<DetailedConnectionDTO> outConnections;
	private List<DetailedCategoryDTO> categories;

	public DetailedChannelDTO(Channel channel) {
		super(channel);
		this.inConnections = channel.getInConnections().stream().map(DetailedConnectionDTO::new).toList();
		this.outConnections = channel.getOutConnections().stream().map(DetailedConnectionDTO::new).toList();
		this.categories = channel.getCategories().stream().map(DetailedCategoryDTO::new).toList();
	}

	public List<DetailedConnectionDTO> getInConnections() {
		return inConnections;
	}

	public List<DetailedConnectionDTO> getOutConnections() {
		return outConnections;
	}

	public List<DetailedCategoryDTO> getCategories() {
		return categories;
	}

}
