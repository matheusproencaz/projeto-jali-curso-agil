package com.mpz.EsseEuJaLi.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.UserLoginDTO;
import com.mpz.EsseEuJaLi.repositories.UserRepository;
import com.mpz.EsseEuJaLi.resources.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserLoginDTO>{

	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserInsert ann) {
	}

	@Override
	public boolean isValid(UserLoginDTO objDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		User aux = repository.findByName(objDTO.getName());
		if(aux != null) {
			list.add(new FieldMessage("name", "Usuário já existe!"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
				.addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
