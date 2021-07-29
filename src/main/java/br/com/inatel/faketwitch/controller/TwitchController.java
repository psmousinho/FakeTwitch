package br.com.inatel.faketwitch.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.faketwitch.modelo.twitch.TwitchSearch;
import br.com.inatel.faketwitch.service.TwitchAPIService;

@RestController
@RequestMapping("/twitch")
public class TwitchController {
	
	//TODO fix this
	private TwitchAPIService twitchAPIService = new TwitchAPIService();
	
	@GetMapping
	public ResponseEntity<TwitchSearch> search(@RequestParam(required = true) String query) {
		return twitchAPIService.search(query);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TwitchSearch> search(@PathVariable("id") Integer id) {
		return twitchAPIService.searchById(id);
	}
}
