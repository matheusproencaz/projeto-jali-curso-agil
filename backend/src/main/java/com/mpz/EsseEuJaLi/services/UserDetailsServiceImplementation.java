package com.mpz.EsseEuJaLi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.repositories.UserRepository;
import com.mpz.EsseEuJaLi.security.UserSS;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = repository.findByName(name);
		
		if(user == null) {
			throw new UsernameNotFoundException(name);
		}
		
		return new UserSS(user.getId(), user.getName(), user.getPassword(), user.getRoles());
	}
}
