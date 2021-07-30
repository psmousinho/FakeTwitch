package br.com.inatel.faketwitch.controller.form;

import org.hibernate.validator.constraints.Length;

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
