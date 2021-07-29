package br.com.inatel.faketwitch.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.inatel.faketwitch.modelo.twitch.TwitchSearch;

@Service
public class TwitchAPIService {

	private static final String CLIENT_ID = "23qag0za1g2gmu6v7jfek0dhwrpap6";
	private static final String AUTH = "Bearer 8w2mkoemmwhqjrf2ivgxyw63aqk6kj";

	private static final String SEARCH_URL = "https://api.twitch.tv/helix/search/channels?query={value}'";
	private static final String SEARCH_BY_ID_URL = "https://api.twitch.tv/helix/channels?broadcaster_id={value}";

	private RestTemplate restTemplate;
	private HttpHeaders defaultHeaders;
	
	public TwitchAPIService() {
		this.defaultHeaders = new HttpHeaders();
		this.defaultHeaders.set("client-id", CLIENT_ID);
		this.defaultHeaders.set("Authorization", AUTH);

		this.restTemplate = (new RestTemplateBuilder()).build();
	}

	public ResponseEntity<TwitchSearch> search(String query) {
		HttpEntity request = new HttpEntity(defaultHeaders);
		ResponseEntity<TwitchSearch> response = this.restTemplate.exchange(SEARCH_URL, HttpMethod.GET, request,
				TwitchSearch.class, query);

		return response;
	}

	public ResponseEntity<TwitchSearch> searchById(Integer id) {
		HttpEntity request = new HttpEntity(defaultHeaders);
		ResponseEntity<TwitchSearch> response = this.restTemplate.exchange(SEARCH_BY_ID_URL, HttpMethod.GET, request,
				TwitchSearch.class, id);

		return response;
	}

}
