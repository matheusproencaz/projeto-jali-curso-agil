package com.mpz.EsseEuJaLi.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.services.validations.UserInsert;
import com.mpz.EsseEuJaLi.services.validations.UserUpdate;

@UserInsert
public class UserLoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Preenchimento Obrigatório")
	private String name;
	
	@NotBlank(message = "Preenchimento Obrigatório")
	@Size(min = 8, max = 120, message = "O tamanho da senha deve ser maior que 8 caracteres")
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
