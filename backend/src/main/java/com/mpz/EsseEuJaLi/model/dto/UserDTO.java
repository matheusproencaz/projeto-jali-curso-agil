package com.mpz.EsseEuJaLi.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.model.User;

public class UserDTO {

	private Long id;
	private String name;
	private Integer points;
	
	private List<Book> books = new ArrayList<>();
	private List<Trophy> trophies = new ArrayList<>();
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.points = user.getPoints();
		this.books = user.getBooks();
		this.trophies = user.getTrophies();
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

	public List<Book> getBooks() {
		return books;
	}

	public List<Trophy> getTrophies() {
		return trophies;
	}
}
