package com.mpz.EsseEuJaLi.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.UserLoginDTO;
import com.mpz.EsseEuJaLi.model.enums.Role;
import com.mpz.EsseEuJaLi.repositories.UserRepository;
import com.mpz.EsseEuJaLi.security.UserSS;
import com.mpz.EsseEuJaLi.services.exceptions.AuthorizationException;
import com.mpz.EsseEuJaLi.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private TrophyService trophyService;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	public List<User> findUsers() {
		return userRepository.findAll();
	}
	
	public User findUserById(Long id) {
		UserSS user = UserSecurityService.authenticated();
		if (user == null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}

		Optional<User> obj = userRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuário não encontrado! id: " + id + ", tipo: " + User.class.getName()));
	}
	
	public User findUserName(String name) {
		UserSS user = UserSecurityService.authenticated();
		if(user == null || !user.hasRole(Role.ADMIN) && !name.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		User obj = userRepository.findByName(user.getUsername());
		
		if(obj == null) {
				throw new ObjectNotFoundException(
						"Usuário não encontrado! id: "+user.getId()+ ", tipo: " + User.class.getName());
		}
		return obj;
	}

	@Transactional
	public User insertUser(User obj) {
		points(obj);
		obj = userRepository.save(obj);
		return obj;
	}

	public void addBook(Long idUser, Long idBook) {
		User user = findUserById(idUser);
		Book book = bookService.findBook(idBook);

		user.addBooks(book);
		points(user);
		user = userRepository.save(user);
	}

	public void removeBook(Long idUser, Long idBook) {
		User user = findUserById(idUser);
		Book book = bookService.findBook(idBook);
		
		if(!user.getBooks().contains(book)) {
			throw new ObjectNotFoundException("Este usuário não tem esse livro!");
		}
		
		user.removeBook(book);
		points(user);
		userRepository.save(user);
	}
	
	public void addTrophy(Long idUser, Long idTrophy) {
		User user = findUserById(idUser);
		Trophy trof = trophyService.findTrophy(idTrophy);

		user.addTrophy(trof);
		user = userRepository.save(user);
	}

	public void remove(Long id) {
		UserSS user = UserSecurityService.authenticated();
		if (user == null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		userRepository.delete(findUserById(id));
	}

	public User fromDTO(UserLoginDTO objDTO) {
		return new User(null, objDTO.getName(), pwEncoder.encode(objDTO.getPassword()));
	}
	
	
	public void points(User user) {
		int pages = user.getBooks()
				.stream()
				.map(p -> p.getPages())
				.reduce(0, (subtotal, bookPages) -> subtotal + bookPages);
		
		if(pages == 0) {
			user.setPoints(0);
			return;
		}
		
		// https://stackoverflow.com/questions/7139382/java-rounding-up-to-an-int-using-math-ceil
		int points =  (pages + 100 - 1)/100;
		user.setPoints(points);
	}
}
