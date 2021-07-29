package br.com.inatel.faketwitch.modelo.twitch;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class TwitchChannel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String broadcaster_language;
	private String broadcaster_login;
	private String display_name;
	private Long game_id;
	private String game_name;
	private Long id;
	private Boolean is_live;
	private List<String> tag_ids;
	private String thumbnail_url;
	private String title;
	private LocalDateTime started_at;
	private Integer delay;

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public String getBroadcaster_language() {
		return broadcaster_language;
	}

	public void setBroadcaster_language(String broadcaster_language) {
		this.broadcaster_language = broadcaster_language;
	}

	public String getBroadcaster_login() {
		return broadcaster_login;
	}

	public void setBroadcaster_login(String broadcaster_login) {
		this.broadcaster_login = broadcaster_login;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public Long getGame_id() {
		return game_id;
	}

	public void setGame_id(Long game_id) {
		this.game_id = game_id;
	}

	public String getGame_name() {
		return game_name;
	}

	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIs_live() {
		return is_live;
	}

	public void setIs_live(Boolean is_live) {
		this.is_live = is_live;
	}

	public List<String> getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(List<String> tag_ids) {
		this.tag_ids = tag_ids;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getStarted_at() {
		return started_at;
	}

	public void setStarted_at(LocalDateTime started_at) {
		this.started_at = started_at;
	}

}
