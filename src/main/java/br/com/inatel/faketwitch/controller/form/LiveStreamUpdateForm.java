package br.com.inatel.faketwitch.controller.form;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.faketwitch.modelo.Category;
import br.com.inatel.faketwitch.modelo.LiveStream;
import br.com.inatel.faketwitch.repository.CategoryRepository;

public class LiveStreamUpdateForm {

	@Length(min = 5)
	private String title;
	private Long gameId;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getTitle() {
		return title;
	}

	public Long getGameId() {
		return gameId;
	}
}
