package br.com.inatel.faketwitch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.faketwitch.modelo.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long>{

	Optional<Channel> findByName(String username);

}
