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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.faketwitch.controller.dto.SimplifiedLiveStreamDTO;
import br.com.inatel.faketwitch.controller.form.LiveStreamForm;
import br.com.inatel.faketwitch.controller.form.LiveStreamUpdateForm;
import br.com.inatel.faketwitch.modelo.Category;
import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.modelo.LiveStream;
import br.com.inatel.faketwitch.repository.CategoryRepository;
import br.com.inatel.faketwitch.repository.ChannelRepository;
import br.com.inatel.faketwitch.repository.LiveStreamRepository;

@RestController
@RequestMapping("/livestream")
public class LiveStreamController {

	@Autowired
	private LiveStreamRepository liveStreamRepository;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping()
	@Cacheable("livestreams")
	public Page<SimplifiedLiveStreamDTO> listLiveStreams(@RequestParam(required = false) Boolean active,
			@PageableDefault(sort = "started", direction = Direction.DESC, page = 0, size = 10) Pageable paging) {

		Page<LiveStream> streams;
		if (active == null) {
			streams = liveStreamRepository.findAll(paging);
		} else {
			streams = liveStreamRepository.findByActive(active, paging);
		}

		return streams.map(SimplifiedLiveStreamDTO::new);
	}

	@GetMapping("/channel/{id}")
	@Cacheable("livestreams")
	public ResponseEntity<List<SimplifiedLiveStreamDTO>> listLiveStreamsByOwner(@PathVariable("id") Long id,
			@RequestParam(required = false) Boolean active) {

		Optional<Channel> channel = channelRepository.findById(id);
		if (channel.isPresent()) {
			List<LiveStream> streams;
			if (active == null) {
				streams = liveStreamRepository.findByOwner(channel.get());
			} else {
				streams = liveStreamRepository.findByOwnerAndActive(channel.get(), active);
			}

			return ResponseEntity.ok(streams.stream().map(SimplifiedLiveStreamDTO::new).toList());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "livestreams", allEntries = true)
	public ResponseEntity<SimplifiedLiveStreamDTO> startLiveSream(@RequestBody @Valid LiveStreamForm form,
			UriComponentsBuilder uriBuilder) {

		LiveStream stream = form.convert(channelRepository, categoryRepository);
		liveStreamRepository.save(stream);

		URI uri = uriBuilder.path("/livestream/{id}").buildAndExpand(stream.getId()).toUri();
		return ResponseEntity.created(uri).body(new SimplifiedLiveStreamDTO(stream));
	}

	@PatchMapping("/{id}")
	@Transactional
	@CacheEvict(value = "livestreams", allEntries = true)
	public ResponseEntity<SimplifiedLiveStreamDTO> updateLiveStream(@PathVariable("id") Long id,
			@RequestBody @Valid LiveStreamUpdateForm form) {

		Optional<LiveStream> streamOpt = liveStreamRepository.findById(id);
		if (streamOpt.isPresent()) {
			LiveStream stream = streamOpt.get();

			if (form.getTitle() != null) {
				stream.setTitle(form.getTitle());
			}

			if (form.getGameId() != null) {
				Optional<Category> categoryOpt = categoryRepository.findById(form.getGameId());
				if (categoryOpt.isPresent()) {
					stream.setGame(categoryOpt.get());
				} else {
					return ResponseEntity.notFound().build();
				}
			}

			return ResponseEntity.ok(new SimplifiedLiveStreamDTO(stream));
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/finish/{id}")
	@Transactional
	@CacheEvict(value = "livestreams", allEntries = true)
	public ResponseEntity<SimplifiedLiveStreamDTO> finishLiveStream(@PathVariable("id") Long id) {

		Optional<LiveStream> streamOpt = liveStreamRepository.findById(id);
		if (streamOpt.isPresent()) {
			LiveStream stream = streamOpt.get();
			if (stream.getStatus()) {
				stream.setStatus(false);
			}

			return ResponseEntity.ok(new SimplifiedLiveStreamDTO(stream));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "livestreams", allEntries = true)
	public ResponseEntity<?> deleteStream(@PathVariable Long id) {
		Optional<LiveStream> optional = liveStreamRepository.findById(id);
		if (optional.isPresent()) {
			liveStreamRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
