package com.mpz.EsseEuJaLi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.enums.Genre;
import com.mpz.EsseEuJaLi.resources.utils.URLDecode;
import com.mpz.EsseEuJaLi.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<Page<Book>> findPageBooks(
			@RequestParam(value = "name", defaultValue = "0") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		String nameDecoded = URLDecode.decodeParam(name);
		
		Page<Book> pageObj = bookService.findAllPageble(nameDecoded, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(pageObj);
	}
	
	@GetMapping("/name")
	public ResponseEntity<Page<Book>> searchBookByName(
			@RequestParam(value = "name", defaultValue = "0") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		String nameDecoded = URLDecode.decodeParam(name);
		
		Page<Book> pageObj = bookService.searchBooksByName(nameDecoded, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(pageObj);
	}

	@GetMapping("/genre/{genre}")
	public ResponseEntity<Page<Book>> searchBookByGenre(
			@PathVariable Integer genre,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Page<Book> pageObj = bookService.searchBooksByGenre(genre, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(pageObj);
	}

	@GetMapping("/genres")
	public ResponseEntity<List<String>> genres(){
		return ResponseEntity.ok(Genre.getDescriptions());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> findBook(@PathVariable Long id){
		return ResponseEntity.ok(bookService.findBook(id));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Book> insertBook(Book obj){
		bookService.insertBook(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeBook(Long id){
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}