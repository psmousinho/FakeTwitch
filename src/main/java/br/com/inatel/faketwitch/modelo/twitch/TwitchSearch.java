package br.com.inatel.faketwitch.modelo.twitch;

import java.io.Serializable;
import java.util.List;

public class TwitchSearch implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<TwitchChannel> data;

	public List<TwitchChannel> getData() {
		return data;
	}

	public void setData(List<TwitchChannel> data) {
		this.data = data;
	}
	
	
	
}
