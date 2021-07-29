package br.com.inatel.faketwitch.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LiveStream {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@ManyToOne
	private Channel owner;
	@ManyToOne
	private Category game;
	private Boolean active = true;
	private LocalDateTime started = LocalDateTime.now();
	private LocalDateTime finished;

	public LiveStream() {
		super();
	}

	public LiveStream(String title, Channel owner, Category game) {
		this.title = title;
		this.owner = owner;
		this.game = game;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Channel getOwner() {
		return owner;
	}

	public void setOwner(Channel owner) {
		this.owner = owner;
	}

	public Category getGame() {
		return game;
	}

	public void setGame(Category game) {
		this.game = game;
	}

	public Boolean getStatus() {
		return active;
	}

	public void setStatus(Boolean status) {
		this.active = status;
		this.finished = LocalDateTime.now();
	}

	public LocalDateTime getStarted() {
		return started;
	}

	public void setStarted(LocalDateTime started) {
		this.started = started;
	}

	public LocalDateTime getFinished() {
		return finished;
	}

	public void setFinished(LocalDateTime finished) {
		this.finished = finished;
	}

}
