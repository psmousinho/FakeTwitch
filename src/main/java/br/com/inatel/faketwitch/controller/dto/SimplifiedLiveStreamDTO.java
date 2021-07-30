package br.com.inatel.faketwitch.controller.dto;

import java.time.LocalDateTime;

import br.com.inatel.faketwitch.modelo.LiveStream;

public class SimplifiedLiveStreamDTO {

	private Long id;
	private String title;
	private SimplifiedChannelDTO owner;
	private SimplifiedCategoryDTO game;
	private Boolean active;
	private LocalDateTime start;
	private LocalDateTime finished;

	public SimplifiedLiveStreamDTO(LiveStream stream) {
		this.id = stream.getId();
		this.title = stream.getTitle();
		this.owner = new SimplifiedChannelDTO(stream.getOwner());
		this.game = new SimplifiedCategoryDTO(stream.getGame());
		this.active = stream.getStatus();
		this.start = stream.getStarted();
		this.finished = stream.getFinished();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public SimplifiedChannelDTO getOwner() {
		return owner;
	}

	public SimplifiedCategoryDTO getGame() {
		return game;
	}

	public Boolean getStatus() {
		return active;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getFinished() {
		return finished;
	}

}
