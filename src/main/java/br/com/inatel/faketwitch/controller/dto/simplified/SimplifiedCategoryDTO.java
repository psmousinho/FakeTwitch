package br.com.inatel.faketwitch.controller.dto.simplified;

import br.com.inatel.faketwitch.modelo.Category;

public class SimplifiedCategoryDTO {

	private Long id;
	private String name;
	private String genre;

	public SimplifiedCategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.genre = category.getGenre();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

}
