package com.mpz.EsseEuJaLi.model.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.User;

public class UserDTO {

	private Long id;
	private String name;
	private Integer points;
	
	private Set<Book> books = new HashSet<>();
	private Set<TrophyDTO> trophies = new HashSet<>();
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.points = user.getPoints();
		this.books = user.getBooks();
		this.trophies = user.getTrophies().stream().map(x -> new TrophyDTO(x)).collect(Collectors.toSet());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getPoints() {
		return points;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public Set<TrophyDTO> getTrophies() {
		return trophies;
	}
}
