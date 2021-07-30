package br.com.inatel.faketwitch.controller.form;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.repository.ChannelRepository;

public class ChannelUpdateForm {
	
	@Length(min = 5)
	private String name;

	@Length(min = 5)
	private String password;

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Channel update(Long id, ChannelRepository channelRepository) {
		Channel channel = channelRepository.getOne(id);

		channel.setName(name);
		channel.setPassword(password);

		return channel;
	}
}
