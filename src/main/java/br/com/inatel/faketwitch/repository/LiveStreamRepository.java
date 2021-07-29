package br.com.inatel.faketwitch.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.LiveStream;

public interface LiveStreamRepository extends JpaRepository<LiveStream, Long>{

	Page<LiveStream> findByActive(Boolean status, Pageable paging);

	List<LiveStream> findByOwner(Channel channel);

	List<LiveStream> findByOwnerAndActive(Channel channel, Boolean status);

	Page<LiveStream> findByOwnerInAndActive(List<Channel> channels, Boolean status,  Pageable paging);

	
}
