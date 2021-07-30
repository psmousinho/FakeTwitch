package br.com.inatel.faketwitch.controller.dto;

import br.com.inatel.faketwitch.modelo.Category;

public class CategoryDTO extends SimplifiedCategoryDTO {

	
	private Integer nFollowers;
	private Integer nStreams;

	public CategoryDTO(Category category) {
		super(category);
		this.nFollowers = category.getFollowers().size();
		this.nStreams = category.getStreams().size();
	}

	public Integer getnFollowers() {
		return nFollowers;
	}

	public Integer getnStreams() {
		return nStreams;
	}

}
