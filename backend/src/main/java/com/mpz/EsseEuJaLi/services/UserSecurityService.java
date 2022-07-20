package com.mpz.EsseEuJaLi.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.security.UserSS;

@Service
public class UserSecurityService {

	public static UserSS authenticated() {	
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
