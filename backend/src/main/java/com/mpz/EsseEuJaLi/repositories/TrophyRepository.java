package com.mpz.EsseEuJaLi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpz.EsseEuJaLi.model.Trophy;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Long>{

}
