package com.mpz.EsseEuJaLi.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerMapping;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.UserUpdatePasswordDTO;
import com.mpz.EsseEuJaLi.repositories.UserRepository;
import com.mpz.EsseEuJaLi.resources.exceptions.FieldMessage;

public class UserUpdateValidator implements ConstraintValidator<UserUpdate, UserUpdatePasswordDTO>{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public void initialize(UserUpdate ann) {
	}
	
	@Override
	public boolean isValid(UserUpdatePasswordDTO objDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		User aux = repository.findByName(objDTO.getUserName());
		
		if(aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("Nome de Usuário", "Nome de usuário já existe!"));
		}
		
		User auxPass = repository.findById(objDTO.getId()).orElse(aux);

		if(encoder.matches(objDTO.getOldPassword(), auxPass.getPassword()) != true) {
			list.add(new FieldMessage("oldPassword", "Sua antiga senha não é essa!"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
				.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
}
