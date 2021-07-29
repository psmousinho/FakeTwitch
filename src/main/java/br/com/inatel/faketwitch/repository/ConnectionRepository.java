package br.com.inatel.faketwitch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.Connection;
import br.com.inatel.faketwitch.modelo.ConnectionStatus;

public interface ConnectionRepository  extends JpaRepository<Connection, Long>{
	
	Page<Connection> findByOwnerId(Long id, Pageable paging);
	
	Connection findByOwnerAndTarget(Channel ownner, Channel target);
	
	Page<Connection> findByOwnerIdAndStatus(Long id, ConnectionStatus status, Pageable paging);

	Page<Connection> findByTargetIdAndStatus(Long id, ConnectionStatus status, Pageable paging);

	Page<Connection> findByOwnerIdAndStatusIsNot(Long id, ConnectionStatus status, Pageable paging);

	Page<Connection> findByTargetIdAndStatusIsNot(Long id, ConnectionStatus follow, Pageable paging);
}
