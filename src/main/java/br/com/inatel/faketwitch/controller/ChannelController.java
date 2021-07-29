package br.com.inatel.faketwitch.controller;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.faketwitch.controller.dto.ChannelDTO;
import br.com.inatel.faketwitch.controller.dto.detailed.DetailedChannelDTO;
import br.com.inatel.faketwitch.controller.dto.simplified.SimplifiedCategoryDTO;
import br.com.inatel.faketwitch.controller.dto.simplified.SimplifiedChannelDTO;
import br.com.inatel.faketwitch.controller.dto.simplified.SimplifiedLiveStreamDTO;
import br.com.inatel.faketwitch.controller.form.ChannelForm;
import br.com.inatel.faketwitch.controller.form.ChannelUpdateForm;
import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.Connection;
import br.com.inatel.faketwitch.modelo.LiveStream;
import br.com.inatel.faketwitch.repository.ChannelRepository;
import br.com.inatel.faketwitch.repository.LiveStreamRepository;

@RestController
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private LiveStreamRepository liveStreamRepository;

	@GetMapping
	@Cacheable(value = "channelList")
	public Page<SimplifiedChannelDTO> listChannels(
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Channel> channels = channelRepository.findAll(paging);
		return channels.map(SimplifiedChannelDTO::new);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetailedChannelDTO> channelDetails(@PathVariable Long id) {
		Optional<Channel> channel = channelRepository.findById(id);
		if (channel.isPresent()) {
			return ResponseEntity.ok(new DetailedChannelDTO(channel.get()));
		}

		return ResponseEntity.notFound().build();
	}

	// TODO Criar Papel admin e permitir que somente ele cadastre novos usuarios
	// pela api
	@PostMapping
	@Transactional
	@CacheEvict(value = "channelList", allEntries = true)
	public ResponseEntity<ChannelDTO> createChannel(@RequestBody @Valid ChannelForm form,
			UriComponentsBuilder uriBuilder) {
		Channel channel = form.convert();
		channelRepository.save(channel);

		URI uri = uriBuilder.path("/channel/{id}").buildAndExpand(channel.getId()).toUri();
		return ResponseEntity.created(uri).body(new ChannelDTO(channel));
	}

	@PatchMapping("/{id}")
	@Transactional
	@CacheEvict(value = "channelList", allEntries = true)
	public ResponseEntity<ChannelDTO> updateChannel(@PathVariable Long id, @RequestBody @Valid ChannelUpdateForm form) {
		Optional<Channel> optional = channelRepository.findById(id);
		if (optional.isPresent()) {
			Channel channel = form.update(id, channelRepository);
			return ResponseEntity.ok(new ChannelDTO(channel));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "channelList", allEntries = true)
	public ResponseEntity<?> deleteChannel(@PathVariable Long id) {
		Optional<Channel> optional = channelRepository.findById(id);
		if (optional.isPresent()) {
			channelRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/live")
	public Page<SimplifiedLiveStreamDTO> listLiveChannels(@PathVariable Long id,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Optional<Channel> channel = channelRepository.findById(id);
		List<Channel> channels = channel.get().getOutConnections().stream().map(Connection::getTarget).toList();

		Page<LiveStream> page = liveStreamRepository.findByOwnerInAndActive(channels, true, paging);

		return page.map(SimplifiedLiveStreamDTO::new);
	}

	@GetMapping("/{id}/videos")
	public Page<SimplifiedLiveStreamDTO> listVODs(@PathVariable Long id,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Optional<Channel> channel = channelRepository.findById(id);
		List<Channel> channels = channel.get().getOutConnections().stream().map(Connection::getTarget).toList();

		Page<LiveStream> page = liveStreamRepository.findByOwnerInAndActive(channels, false, paging);

		return page.map(SimplifiedLiveStreamDTO::new);
	}

	@GetMapping("/{id}/categories")
	public ResponseEntity<List<SimplifiedCategoryDTO>> listCategories(@PathVariable Long id,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Optional<Channel> channel = channelRepository.findById(id);

		if (channel.isPresent()) {
			List<SimplifiedCategoryDTO> categories = channel.get().getCategories().stream()
					.map(SimplifiedCategoryDTO::new).toList();
			return ResponseEntity.ok(categories);
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping("/{id}/channels")
	@Cacheable(value = "channelList")
	public ResponseEntity<List<ChannelDTO>> listOutChannels(@PathVariable Long id) {

		Optional<Channel> channel = channelRepository.findById(id);
		if (channel.isPresent()) {
			List<Channel> channels = channel.get().getOutConnections().stream().map(Connection::getTarget).toList();
			List<ChannelDTO> dtos = channels.stream().map(ChannelDTO::new).toList();
			return ResponseEntity.ok(dtos);
		}

		return ResponseEntity.notFound().build();
	}

}