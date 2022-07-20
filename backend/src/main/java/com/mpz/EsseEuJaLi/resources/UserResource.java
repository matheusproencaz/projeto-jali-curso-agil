package com.mpz.EsseEuJaLi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.UserDTO;
import com.mpz.EsseEuJaLi.model.dto.UserLoginDTO;
import com.mpz.EsseEuJaLi.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
		return ResponseEntity.ok().body(new UserDTO(userService.findUserById(id)));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAll(){
		return ResponseEntity.ok(userService.findUsers());
	}
	
	@PostMapping
	public ResponseEntity<Void> insertUser(@RequestBody UserLoginDTO objDTO){
		
		User obj = userService.fromDTO(objDTO);
		userService.insertUser(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build(); 
	}
	
	@PatchMapping("/{idUser}/addBook/{idBook}")
	public ResponseEntity<Void> addBookToUser(@PathVariable Long idUser, @PathVariable Long idBook) {
		userService.addBook(idUser, idBook);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{idUser}/removeBook/{idBook}")
	public ResponseEntity<Void> removeBookToUser(@PathVariable Long idUser, @PathVariable Long idBook) {
		userService.removeBook(idUser, idBook);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{idUser}/trophy/{idTrophy}")
	public ResponseEntity<Void> addTrophyToUser(@PathVariable Long idUser, @PathVariable Long idTrophy){
		userService.addTrophy(idUser, idTrophy);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeUser(@PathVariable Long idUser){
		userService.remove(idUser);
		return ResponseEntity.noContent().build();
	}
}