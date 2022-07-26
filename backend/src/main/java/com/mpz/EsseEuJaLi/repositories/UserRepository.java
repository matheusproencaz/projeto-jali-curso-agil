package com.mpz.EsseEuJaLi.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpz.EsseEuJaLi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Transactional(readOnly = true)
	User findByName(String name);
	
//	@Query("SELECT u FROM User u JOIN FETCH u.books JOIN FETCH u.trophies WHERE u.id = :id")
//	User findUserInformation(@Param("id") Long id);
}
