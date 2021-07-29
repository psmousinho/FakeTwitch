package br.com.inatel.faketwitch.controller.form;

import br.com.inatel.faketwitch.modelo.Category;
import br.com.inatel.faketwitch.repository.CategoryRepository;

public class CategoryForm {

	private String name;
	private String genre;

	public void setName(String name) {
		this.name = name;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Category convert() {
		return new Category(this.name, this.genre);
	}

	public Category update(Long id, CategoryRepository categoryRepository) {
		Category category = categoryRepository.getOne(id);

		if (this.name != null) {
			category.setName(this.name);
		}
		if (this.genre != null) {
			category.setGenre(this.genre);
		}

		return category;
	}

}
