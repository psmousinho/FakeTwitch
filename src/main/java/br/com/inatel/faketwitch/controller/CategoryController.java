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

import br.com.inatel.faketwitch.controller.dto.CategoryDTO;
import br.com.inatel.faketwitch.controller.dto.DetailedCategoryDTO;
import br.com.inatel.faketwitch.controller.dto.SimplifiedCategoryDTO;
import br.com.inatel.faketwitch.controller.dto.SimplifiedChannelDTO;
import br.com.inatel.faketwitch.controller.dto.SimplifiedLiveStreamDTO;
import br.com.inatel.faketwitch.controller.form.CategoryForm;
import br.com.inatel.faketwitch.modelo.Category;
import br.com.inatel.faketwitch.modelo.Channel;
import br.com.inatel.faketwitch.repository.CategoryRepository;
import br.com.inatel.faketwitch.repository.ChannelRepository;
import br.com.inatel.faketwitch.repository.LiveStreamRepository;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private LiveStreamRepository liveStreamRepository;

	@GetMapping()
	@Cacheable("categories")
	public Page<SimplifiedCategoryDTO> listCategories(
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {
		Page<Category> categories = categoryRepository.findAll(paging);
		return categories.map(SimplifiedCategoryDTO::new);
	}

	@GetMapping("/genre/{genre}")
	@Cacheable("categoriesByGenre")
	public Page<SimplifiedCategoryDTO> listCategoriesByGenre(@PathVariable("genre") String genre,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paging) {

		Page<Category> categories = categoryRepository.findByGenre(genre, paging);
		return categories.map(SimplifiedCategoryDTO::new);
	}

	@GetMapping("{id}")
	public ResponseEntity<DetailedCategoryDTO> categoriesDetails(@PathVariable("id") Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(new DetailedCategoryDTO(category.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/followers")
	public ResponseEntity<List<SimplifiedChannelDTO>> categoryFollowers(@PathVariable("id") Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get().getFollowers().stream().map(SimplifiedChannelDTO::new).toList());
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/streams")
	public ResponseEntity<List<SimplifiedLiveStreamDTO>> categoryStreams(@PathVariable("id") Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get().getStreams().stream().map(SimplifiedLiveStreamDTO::new).toList());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "categories", allEntries = true)
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryForm form,
			UriComponentsBuilder uriBuilder) {

		Category category = form.convert();
		categoryRepository.save(category);

		URI uri = uriBuilder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoryDTO(category));
	}

	@PatchMapping("/{id}")
	@Transactional
	@CacheEvict(value = "categories", allEntries = true)
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id,
			@RequestBody @Valid CategoryForm form) {

		Optional<Category> categoryOpt = categoryRepository.findById(id);
		if (categoryOpt.isPresent()) {
			Category category = form.update(id, categoryRepository);
			return ResponseEntity.ok(new CategoryDTO(category));
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("{categoryId}/addfollow/{channelId}")
	@Transactional
	@CacheEvict(value = "categories", allEntries = true)
	public ResponseEntity<DetailedCategoryDTO> addFollow(@PathVariable("categoryId") Long categoryId,
			@PathVariable("channelId") Long channelId) {

		Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
		Optional<Channel> channelOpt = channelRepository.findById(channelId);

		if (categoryOpt.isPresent() && channelOpt.isPresent()) {
			Category category = categoryOpt.get();
			Channel channel = channelOpt.get();

			//category.getFollowers().add(channel);
			channel.getCategories().add(category);

			//categoryRepository.save(category);
			channelRepository.save(channel);
			return ResponseEntity.ok(new DetailedCategoryDTO(category));
		}

		return ResponseEntity.notFound().build();
	}
	
	/*
	@PatchMapping("{categoryId}/addlivestream/{streamId}")
	@Transactional
	@CacheEvict(value = "categories", allEntries = true)
	public ResponseEntity<DetailedCategoryDTO> addStream(@PathVariable("categoryId") Long categoryId,
			@PathVariable("streamId") Long streamId) {

		Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
		Optional<LiveStream> streamOpt = liveStreamRepository.findById(streamId);

		if (categoryOpt.isPresent() && streamOpt.isPresent()) {
			Category category = categoryOpt.get();
			LiveStream stream = streamOpt.get();
			category.getStreams().add(stream);
			// stream.setGame(category);
			categoryRepository.save(category);
			// liveStreamRepository.save(stream);
			return ResponseEntity.ok(new DetailedCategoryDTO(category));
		}

		return ResponseEntity.notFound().build();
	}
	*/
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "categories", allEntries = true)
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		Optional<Category> optional = categoryRepository.findById(id);
		if (optional.isPresent()) {
			categoryRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
