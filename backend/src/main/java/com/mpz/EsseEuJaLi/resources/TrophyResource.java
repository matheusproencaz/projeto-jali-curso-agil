package com.mpz.EsseEuJaLi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.model.dto.TrophyDTO;
import com.mpz.EsseEuJaLi.services.TrophyService;

@PreAuthorize("hasAnyRole('ADMIN')")
@RestController
@RequestMapping(value = "/trophies")
public class TrophyResource {

	@Autowired
	private TrophyService trophyService;
	
	@GetMapping
	public ResponseEntity<List<TrophyDTO>> findAllTrophies() {
		List<Trophy> list = trophyService.findAllTrophies();
		List<TrophyDTO> listDTO = list.stream().map(x -> new TrophyDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TrophyDTO> findTrophyById(@PathVariable Long id){
		return ResponseEntity.ok().body(new TrophyDTO(trophyService.findTrophy(id)));
	}
	
	@PostMapping
	public ResponseEntity<Trophy> insertTrophy(@Valid @RequestBody Trophy trophy) {
		
		trophyService.insertTrophy(trophy);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(trophy.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTrophy(@PathVariable Long id){
		trophyService.deleteTrophy(id);
		
		return ResponseEntity.noContent().build();
	}
}
