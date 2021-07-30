package br.com.inatel.faketwitch.repository;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.Connection;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ConnectionRepositoryTest {

	@Autowired
	ChannelRepository channelRepository;
	@Autowired
	private ConnectionRepository connectionRepository;

	private Channel owner;
	private Channel target;

	@Before
	public void before() {
		this.owner = channelRepository.getOne(Long.valueOf(1));
		this.target = channelRepository.getOne(Long.valueOf(2));
		

	}

	@Test
	public void shouldFindConnectionByOwnerAndTarget() {

		Connection connection = connectionRepository.findByOwnerAndTarget(owner, target);
		Assert.assertNotNull(connection);
		Assert.assertEquals(owner.getId(), connection.getOwner().getId());
		Assert.assertEquals(target.getId(), connection.getTarget().getId());
	}
	
	@Test
	public void shouldNotFindConnectionByOwnerAndTarget() {

		Connection connection = connectionRepository.findByOwnerAndTarget(target, owner);
		Assert.assertNull(connection);
	}

}
