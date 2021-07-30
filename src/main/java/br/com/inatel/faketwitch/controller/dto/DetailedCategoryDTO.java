package br.com.inatel.faketwitch.controller.dto;

import java.util.List;

import br.com.inatel.faketwitch.modelo.Category;

public class DetailedCategoryDTO extends CategoryDTO {

	private List<ChannelDTO> followers;
	private List<SimplifiedLiveStreamDTO> streams;

	public DetailedCategoryDTO(Category category) {
		super(category);

		this.followers = category.getFollowers().stream().map(ChannelDTO::new).toList();
		this.streams = category.getStreams().stream().map(SimplifiedLiveStreamDTO::new).toList();
	}

	public List<ChannelDTO> getFollowers() {
		return followers;
	}

	public List<SimplifiedLiveStreamDTO> getStreams() {
		return streams;
	}

}
