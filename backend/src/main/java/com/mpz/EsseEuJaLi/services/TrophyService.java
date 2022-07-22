package com.mpz.EsseEuJaLi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.repositories.TrophyRepository;
import com.mpz.EsseEuJaLi.services.exceptions.DataIntegrityException;
import com.mpz.EsseEuJaLi.services.exceptions.ObjectNotFoundException;

@Service
public class TrophyService {

	@Autowired
	private TrophyRepository trophyRepository;
	
	public Trophy findTrophy(Long id){
		Optional<Trophy> obj = trophyRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tropheu não encontrado! Id: "+id+", Tipo: " + Trophy.class.getName()));
	}
	
	public List<Trophy> findAllTrophies(){
		return trophyRepository.findAll();
	}
	
	public Trophy insertTrophy(Trophy trophy) {
		trophy = trophyRepository.save(trophy);
		return trophy;
	}
	
	public void deleteTrophy(Long id) {
		
		try {
			trophyRepository.delete(findTrophy(id));
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Troféu que possui usuários.");
		}
	}
	
}