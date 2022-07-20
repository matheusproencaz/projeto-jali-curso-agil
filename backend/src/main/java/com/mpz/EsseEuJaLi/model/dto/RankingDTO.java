package com.mpz.EsseEuJaLi.model.dto;

import com.mpz.EsseEuJaLi.model.User;

public class RankingDTO implements Comparable<RankingDTO>{

	private String name;
	private Integer points;
	
	public RankingDTO(User user) {
		this.name = user.getName();
		this.points = user.getPoints();
	}

	public String getName() {
		return name;
	}

	public Integer getPoints() {
		return points;
	}

	@Override
	public int compareTo(RankingDTO user) {
		return this.points.compareTo(user.getPoints());
	}
}
