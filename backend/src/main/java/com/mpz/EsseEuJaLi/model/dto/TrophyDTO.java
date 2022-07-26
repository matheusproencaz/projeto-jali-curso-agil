package com.mpz.EsseEuJaLi.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mpz.EsseEuJaLi.model.Trophy;

public class TrophyDTO {

	private Long id;
	private String name;
	private String genre;
	
	private List<RankingDTO> users = new ArrayList<>();
	
	public TrophyDTO(Trophy t) {
		this.id = t.getId();
		this.name = t.getName();
		this.genre = t.getGenre().getDescription();
		this.users = t.getUsers()
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<RankingDTO> getUsers() {
		return users;
	}

	public void setUsers(List<RankingDTO> users) {
		this.users = users;
	}
}
