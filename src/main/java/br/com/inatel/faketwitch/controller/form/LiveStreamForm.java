package br.com.inatel.faketwitch.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.faketwitch.modelo.Category;
import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.LiveStream;
import br.com.inatel.faketwitch.repository.CategoryRepository;
import br.com.inatel.faketwitch.repository.ChannelRepository;

public class LiveStreamForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String title;
	@NotNull
	private Long ownerId;
	@NotNull
	private Long gameId;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOwnerId(Long owenerId) {
		this.ownerId = owenerId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public LiveStream convert(ChannelRepository channelRepository, CategoryRepository categoryRepository) {
		Channel channel = channelRepository.getOne(ownerId);
		Category category = categoryRepository.getOne(gameId);
		
		LiveStream stream = new LiveStream(title, channel, category);
		
		category.getStreams().add(stream);
		
		return stream;
	}

}
