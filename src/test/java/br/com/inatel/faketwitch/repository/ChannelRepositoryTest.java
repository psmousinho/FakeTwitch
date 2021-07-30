package br.com.inatel.faketwitch.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.faketwitch.modelo.Channel;

@RunWith(SpringRunner.class)
@DataJpaTest()
@ActiveProfiles("test")
public class ChannelRepositoryTest {

	@Autowired
	ChannelRepository channelRepository;

	@Test
	public void ShouldFindByName() {
		String name = "alpha";
		Optional<Channel> channel = channelRepository.findByName(name);
		Assert.assertNotNull(channel.get());
		Assert.assertEquals(name, channel.get().getName());
	}
	
	@Test
	public void ShouldNotFindByName() {
		String name = "notAnUser";
		Optional<Channel> channel = channelRepository.findByName(name);
		Assert.assertFalse(channel.isPresent());
	}

}
