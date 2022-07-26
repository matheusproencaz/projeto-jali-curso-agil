package com.mpz.EsseEuJaLi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpz.EsseEuJaLi.model.Trophy;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Long>{

	@Transactional(readOnly = true)
	Trophy findByName(String name);
	
}
