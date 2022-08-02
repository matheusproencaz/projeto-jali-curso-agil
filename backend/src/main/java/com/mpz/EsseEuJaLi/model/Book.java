package com.mpz.EsseEuJaLi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpz.EsseEuJaLi.model.enums.Genre;

@Entity
@Table(name = "BOOKS")
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 255, message = "O nome do livro deve ter entre 3 a 255 caracteres")
	private String name;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer pages;
	
	@NotNull(message = "Preenchimento Obrigatório")
	private Integer genre;
	
	private String urlImg;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "books")
	private List<User> users = new ArrayList<>();
	
	public Book() {
	}

	public Book(Long id, String name, Integer pages, Genre genre, String urlImg) {
		this.id = id;
		this.name = name;
		this.pages = pages;
		this.genre = (genre == null) ? null : genre.getCod();
		this.urlImg = urlImg;
	}

	public Long getId() {
		return id;
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

	public Genre getGenre() {
		return Genre.toEnum(genre);
	}

	public void setType(Genre genre) {
		this.genre = genre.getCod();
	}
	
	public String getUrlImg() {
		return urlImg;
	}
	
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	
	public List<User> getUsers (){
		return users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(genre, id, name, pages);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(genre, other.genre) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(pages, other.pages);
	}
}
