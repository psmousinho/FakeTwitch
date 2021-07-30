package br.com.inatel.faketwitch.controller;


import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@EnableAutoConfiguration
public class AutenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void ShouldReturnBadRequestForWrongCredentials() throws Exception {
		URI uri = new URI("/auth");
		String data = "{\"username\":\"alpha\", \"password\":\"wrongpassword\"}";

		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(data)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldExecuteLogin() throws Exception {
		URI uri = new URI("/auth");
		String data = "{\"username\":\"alpha\", \"password\":\"1234567\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(data).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
}
