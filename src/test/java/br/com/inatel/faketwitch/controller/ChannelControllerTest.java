package br.com.inatel.faketwitch.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ChannelControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldShowChannelDetails() throws Exception {
		URI uri = new URI("/channel/1");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("name")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("nLiveStreams")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("nInConnections")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("nOutConnections")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("nCategories")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("inConnections")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("outConnections")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("categories")));
	}
	
	@Test
	public void shouldCreateUser() throws Exception {
		URI uri = new URI("/channel");
		String data = "{\"name\":\"Lambda\",\"password\":\"1234567\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(data)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		
	}

}
