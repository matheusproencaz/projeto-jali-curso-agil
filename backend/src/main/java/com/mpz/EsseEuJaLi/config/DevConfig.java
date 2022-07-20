package com.mpz.EsseEuJaLi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mpz.EsseEuJaLi.services.DBService;

@Configuration
@Profile("test")
public class DevConfig {

	@Autowired
	private DBService service;

	@Bean
	public boolean instantiateDatabase() {
		service.instantiateTestDatabase();
		return true;
	}
}
