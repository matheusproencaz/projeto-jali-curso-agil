package com.mpz.EsseEuJaLi.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mpz.EsseEuJaLi.services.validations.UserUpdate;

@UserUpdate
public class UserUpdatePasswordDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento Obrigatório")
	private Long id;
	
	@NotBlank(message = "Preenchimento Obrigatório")
	@Size(min = 5, max = 32, message = "O tamanho deve ser entre 5 e 32 caracteres")
	private String userName;

	private String oldPassword;
	
	@NotBlank(message = "Preenchimento Obrigatório")
	@Size(min = 8, max = 120, message = "A senha deve ter pelo menos 8 caracteres")
	private String newPassword;
	
	public UserUpdatePasswordDTO() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}
