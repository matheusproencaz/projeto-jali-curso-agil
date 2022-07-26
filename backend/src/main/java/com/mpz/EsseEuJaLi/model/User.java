package com.mpz.EsseEuJaLi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mpz.EsseEuJaLi.model.enums.Role;

@Entity
@Table(name = "USERS")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	private String password;
	
	private Integer points;
	
	@ManyToMany
	@JoinTable(
		name="marked_books",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<Book> books = new HashSet<>();
	
	@ManyToMany
	@JoinTable(
		name="user_trophies",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "trophy_id"))
	private Set<Trophy> trophies = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ROLES")
	private Set<Integer> roles = new HashSet<>();
	
	public User() {
	}
	
	public User(Long id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.points = 0;
		addRole(Role.USER); // Todo usuário cadastrado é usuário.
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Set<Book> getBooks() {
		return books;
	}
	
	public void addBooks(Book book) {
		this.books.add(book);
	}

	public void removeBook(Book book) {
		this.books.remove(book);
	}
	
	public Set<Trophy> getTrophies() {
		return trophies;
	}
		
	public void addTrophyManually(Trophy trophy) {
		this.trophies.add(trophy);
	}
	
	public Set<Role> getRoles() {
		return roles.stream().map(x -> Role.toEnum(x)).collect(Collectors.toSet());
	}

	public void addRole(Role role) {
		roles.add(role.getCod());
	}
}
