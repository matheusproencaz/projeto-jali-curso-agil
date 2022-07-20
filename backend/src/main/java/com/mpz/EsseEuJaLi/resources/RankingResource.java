package com.mpz.EsseEuJaLi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpz.EsseEuJaLi.model.dto.RankingDTO;
import com.mpz.EsseEuJaLi.services.RankingService;

@RestController
@RequestMapping(value = "/ranking")
public class RankingResource {

	@Autowired
	private RankingService rankingService;
	
	@GetMapping()
	public ResponseEntity<Page<RankingDTO>> ranking(@RequestParam(value="page", defaultValue = "0") Integer page){
		return ResponseEntity.ok(rankingService.ranking(page));
	}
}
