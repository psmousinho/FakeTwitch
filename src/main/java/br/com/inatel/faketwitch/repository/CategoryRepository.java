package br.com.inatel.faketwitch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.faketwitch.modelo.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Page<Category> findByGenre(String genre, Pageable paging);

}
