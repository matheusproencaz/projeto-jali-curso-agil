package com.mpz.EsseEuJaLi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.repositories.BookRepository;
import com.mpz.EsseEuJaLi.services.exceptions.ObjectNotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public Book findBook(Long id){
		Optional<Book> obj = bookRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Livro não encontrado! Id: "+id+", Tipo: " + Book.class.getName()));
	}
	
	public Page<Book> findAllPageble(String name, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return bookRepository.findAll(pageRequest);
	}
	
	public Page<Book> searchBooksByName(String name, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return bookRepository.findDistinctByNameContaining(name, pageRequest);
	}
	
	public Page<Book> searchBooksByGenre(Integer genre, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return bookRepository.findByGenre(genre, pageRequest);
	}
	
	@Transactional
	public Book insertBook(Book obj) {
		bookRepository.save(obj);
		return obj;
	}
	
	@Transactional
	public void deleteBook(Long id) {
		Book obj = findBook(id);
		bookRepository.delete(obj);
	}
}