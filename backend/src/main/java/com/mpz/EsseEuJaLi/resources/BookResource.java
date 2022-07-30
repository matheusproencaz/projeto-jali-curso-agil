package com.mpz.EsseEuJaLi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.dto.BookDTO;
import com.mpz.EsseEuJaLi.model.enums.Genre;
import com.mpz.EsseEuJaLi.resources.utils.URLDecode;
import com.mpz.EsseEuJaLi.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<Page<BookDTO>> searchBookByName(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "genre", defaultValue = "0") Integer genre,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		String nameDecoded = URLDecode.decodeParam(name);
		
		Page<Book> pageObj = bookService.searchBooks(nameDecoded, genre, page, linesPerPage, orderBy, direction);
		Page<BookDTO> objDTO = pageObj.map(x -> new BookDTO(x));
		
		return ResponseEntity.ok(objDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<BookDTO>> searchBookByName(){
		
		List<Book> list = bookService.findAllBooks();
		List<BookDTO> listDTO = list.stream().map(x -> new BookDTO(x))
					.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO );
	}
	
	@GetMapping("/genres")
	public ResponseEntity<List<String>> genres(){
		return ResponseEntity.ok(Genre.getDescriptions());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> findBook(@PathVariable Long id){
		Book obj = bookService.findBook(id);
		BookDTO objDTO = new BookDTO(obj); 
		return ResponseEntity.ok(objDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Book> insertBook(@Valid @RequestBody Book obj){
		bookService.insertBook(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeBook(@PathVariable Long id){
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}