package com.mpz.EsseEuJaLi.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mpz.EsseEuJaLi.model.Book;

public class BookDTO {
	
	private Long id;
	private String name;
	private Integer pages;
	private String genre;
	private String urlImg;
	private List<RankingDTO> users = new ArrayList<>();

	public BookDTO(Book book) {
		this.id = book.getId();
		this.name = book.getName();
		this.pages = book.getPages();
		this.genre = book.getGenre().getDescription();
		this.urlImg = book.getUrlImg();
		this.users = book.getUsers()
						 .stream()
						 .map(x -> new RankingDTO(x))
						 .collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public List<RankingDTO> getUsers() {
		return users;
	}

	public void setUsers(List<RankingDTO> users) {
		this.users = users;
	}
}
