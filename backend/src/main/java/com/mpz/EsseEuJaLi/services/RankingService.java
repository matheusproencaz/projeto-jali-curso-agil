package com.mpz.EsseEuJaLi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.RankingDTO;
import com.mpz.EsseEuJaLi.repositories.UserRepository;

@Service
public class RankingService {

	@Autowired
	private UserRepository userRepository;

	public Page<RankingDTO> ranking(Integer page) {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("points").descending());
		Page<User> users = userRepository.findAll(pageRequest);
		return users.map(user -> new RankingDTO(user)); 
	}
}
