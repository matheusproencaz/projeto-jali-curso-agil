package com.mpz.EsseEuJaLi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpz.EsseEuJaLi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Transactional(readOnly = true)
	Page<Book> findDistinctByNameContaining(String name, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	Page<Book> findByGenre(Integer genre, Pageable pageRequest);
}
