package br.com.inatel.faketwitch.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.faketwitch.controller.dto.ConnectionDTO;
import br.com.inatel.faketwitch.controller.dto.DetailedConnectionDTO;
import br.com.inatel.faketwitch.controller.dto.SimplifiedConnectionDTO;
import br.com.inatel.faketwitch.controller.form.ConnectionForm;
import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.Connection;
import br.com.inatel.faketwitch.modelo.ConnectionStatus;
import br.com.inatel.faketwitch.repository.ChannelRepository;
import br.com.inatel.faketwitch.repository.ConnectionRepository;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private ConnectionRepository connectionRepository;

	@GetMapping("/{id}")
	@Cacheable("channelConections")
	public Page<DetailedConnectionDTO> listConnections(@PathVariable("id") Long id,
			@PageableDefault(sort = "status", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Connection> connections = connectionRepository.findByOwnerId(id, paging);
		return connections.map(DetailedConnectionDTO::new);
	}

	@GetMapping("/{id}/{targetId}") //
	public ResponseEntity<SimplifiedConnectionDTO> getConnection(@PathVariable("id") Long id,
			@PathVariable("targetId") Long targetId) {

		Optional<Channel> owner = channelRepository.findById(id);
		Optional<Channel> target = channelRepository.findById(targetId);
		if (owner.isPresent() && target.isPresent()) {

			Connection previousConnectionTest = connectionRepository.findByOwnerAndTarget(owner.get(), target.get());
			if (previousConnectionTest != null) {
				return ResponseEntity.ok(new SimplifiedConnectionDTO(previousConnectionTest));
			}
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/following")
	@Cacheable(value = "listFollowing")
	public Page<ConnectionDTO> listFollows(@PathVariable("id") Long id,
			@PageableDefault(sort = "status", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Connection> connections = connectionRepository.findByOwnerIdAndStatus(id, ConnectionStatus.FOLLOW, paging);
		return connections.map(ConnectionDTO::new);
	}

	@GetMapping("/{id}/followers")
	@Cacheable(value = "listFollowers")
	public Page<DetailedConnectionDTO> listFollowers(@PathVariable("id") Long id,
			@PageableDefault(sort = "status", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Connection> connections = connectionRepository.findByTargetIdAndStatus(id, ConnectionStatus.FOLLOW,
				paging);
		return connections.map(DetailedConnectionDTO::new);
	}

	@GetMapping("/{id}/subscriptions")
	@Cacheable(value = "listSubscriptions")
	public Page<ConnectionDTO> listSubscriptions(@PathVariable("id") Long id,
			@PageableDefault(sort = "status", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Connection> connections = connectionRepository.findByOwnerIdAndStatusIsNot(id, ConnectionStatus.FOLLOW,
				paging);
		return connections.map(ConnectionDTO::new);
	}

	@GetMapping("/{id}/subscribers")
	@Cacheable(value = "listSubscribers")
	public Page<DetailedConnectionDTO> listSubscribers(@PathVariable("id") Long id,
			@PageableDefault(sort = "status", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Connection> connections = connectionRepository.findByTargetIdAndStatusIsNot(id, ConnectionStatus.FOLLOW,
				paging);
		return connections.map(DetailedConnectionDTO::new);
	}

	@PostMapping("/{id}/{targetId}")
	@Transactional
	@CacheEvict(value = {"channelConections","listFollowing", "listFollowers", "listSubscriptions", "listSubscribers" }, allEntries = true)
	public ResponseEntity<SimplifiedConnectionDTO> createConnection(@PathVariable("id") Long id,
			@PathVariable("targetId") Long targetId,  @RequestBody @Valid ConnectionForm form) {

		Optional<Channel> owner = channelRepository.findById(id);
		Optional<Channel> target = channelRepository.findById(targetId);
		if (owner.isPresent() && target.isPresent()) {

			Connection previousConnectionTest = connectionRepository.findByOwnerAndTarget(owner.get(), target.get());
			if (previousConnectionTest == null) {

				Connection newConnection = new Connection(owner.get(), target.get());
				newConnection.setStatus(form.getStatus());
				connectionRepository.save(newConnection);
				return ResponseEntity.ok(new SimplifiedConnectionDTO(newConnection));
			} else {
				return ResponseEntity.status(403).build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PatchMapping("/{id}/{targetId}")
	@Transactional
	@CacheEvict(value = {"channelConections","listFollowing", "listFollowers", "listSubscriptions", "listSubscribers" }, allEntries = true)
	public ResponseEntity<SimplifiedConnectionDTO> updateConnection(@PathVariable("id") Long id,
			@PathVariable("targetId") Long targetId, @RequestBody @Valid ConnectionForm form) {

		Optional<Channel> owner = channelRepository.findById(id);
		Optional<Channel> target = channelRepository.findById(targetId);
		if (owner.isPresent() && target.isPresent()) {

			Connection previousConnection = connectionRepository.findByOwnerAndTarget(owner.get(), target.get());
			if (previousConnection != null) {
				previousConnection.setStatus(form.getStatus());
				return ResponseEntity.ok(new SimplifiedConnectionDTO(previousConnection));
			}
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}/{targetId}")
	@Transactional
	@CacheEvict(value = {"channelConections","listFollowing", "listFollowers", "listSubscriptions", "listSubscribers" }, allEntries = true)
	public ResponseEntity<?> deleteConnection(@PathVariable("id") Long id, @PathVariable("targetId") Long targetId) {

		Optional<Channel> owner = channelRepository.findById(id);
		Optional<Channel> target = channelRepository.findById(targetId);
		if (owner.isPresent() && target.isPresent()) {

			Connection previousConnection = connectionRepository.findByOwnerAndTarget(owner.get(), target.get());
			if (previousConnection != null) {
				connectionRepository.deleteById(previousConnection.getId());
				return ResponseEntity.ok().build();
			}
		}

		return ResponseEntity.notFound().build();
	}

}
