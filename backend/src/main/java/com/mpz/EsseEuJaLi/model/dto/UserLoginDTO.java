package com.mpz.EsseEuJaLi.model.dto;

import java.io.Serializable;

import com.mpz.EsseEuJaLi.model.User;

public class UserLoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	
	public UserLoginDTO() {
	}

	public UserLoginDTO(User user) {
		this.name = user.getName();
		this.password = user.getPassword();
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
}
