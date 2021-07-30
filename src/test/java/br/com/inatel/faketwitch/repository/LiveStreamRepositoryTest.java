package br.com.inatel.faketwitch.repository;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.LiveStream;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class LiveStreamRepositoryTest {

	private Channel channel;

	@Autowired
	ChannelRepository channelRepository;
	
	@Autowired
	LiveStreamRepository liveStreamRepository;

	@Before
	public void before() {
		this.channel = channelRepository.getOne(Long.valueOf(1));

	}

	@Test
	public void OwnersShouldMatch() {
		List<LiveStream> streams = liveStreamRepository.findByOwner(channel);
		Assert.assertNotNull(streams);
		Assert.assertEquals(channel.getId(), streams.get(0).getOwner().getId());
		
	}
	
	@Test
	public void AcitveShouldByTrue() {
		List<LiveStream> streams = liveStreamRepository.findByOwnerAndActive(channel, true);
		Assert.assertNotNull(streams);
		Assert.assertTrue(streams.get(0).getStatus());
		
	}
	
	@Test
	public void AcitveShouldByFalse() {
		List<LiveStream> streams = liveStreamRepository.findByOwnerAndActive(channel, false);
		Assert.assertNotNull(streams);
		Assert.assertFalse(streams.get(0).getStatus());
		
	}

}
