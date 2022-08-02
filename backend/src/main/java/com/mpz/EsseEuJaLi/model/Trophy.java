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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpz.EsseEuJaLi.model.enums.Genre;

@Entity
@Table(name = "TROPHIES")
public class Trophy implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Preenchimento Obrigatório")
	@Size(min = 3, max = 255, message = "O nome do livro deve ter entre 3 a 255 caracteres")
	private String name;
	
	private Integer genre;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "trophies")
	private List<User> users = new ArrayList<>();
	
	public Trophy() {
	}
	
	public Trophy(Long id, String name, Genre genre) {
		this.id = id;
		this.name = name;
		this.genre = (genre == null) ? null : genre.getCod();
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

	public Genre getGenre() {
		return Genre.toEnum(genre);
	}

	public void setType(Genre genre) {
		this.genre = genre.getCod();
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(genre, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trophy other = (Trophy) obj;
		return Objects.equals(genre, other.genre) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
