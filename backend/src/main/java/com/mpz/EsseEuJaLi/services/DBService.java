package com.mpz.EsseEuJaLi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.enums.Genre;
import com.mpz.EsseEuJaLi.model.enums.Role;
import com.mpz.EsseEuJaLi.repositories.BookRepository;
import com.mpz.EsseEuJaLi.repositories.TrophyRepository;
import com.mpz.EsseEuJaLi.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrophyRepository trophyRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void instantiateTestDatabase() {
		
		User admin = new User(null, "ADM", passwordEncoder.encode("123"));

		User user1 = new User(null, "Matheus", passwordEncoder.encode("123"));
		User user2 = new User(null, "Robson", passwordEncoder.encode("123"));
		User user3 = new User(null, "Roger", passwordEncoder.encode("123"));
		
		Trophy trof1 = new Trophy(null, "Leitor de Ação!", Genre.Acao);
		
		Book book1 = new Book(null, "Livro 1", 500, Genre.Acao, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book2 = new Book(null, "Livro 2", 100, Genre.Aventura, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book3 = new Book(null, "Livro 3", 150, Genre.Biografia, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book4 = new Book(null, "Livro 4", 99, Genre.Terror, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book5 = new Book(null, "Livro 5", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		
		user1.getBooks().addAll(Arrays.asList(book1, book2));
		user2.getBooks().addAll(Arrays.asList(book1, book3));
		user3.getBooks().addAll(Arrays.asList(book4, book1, book5));
		
		book1.getUsers().addAll(Arrays.asList(user1, user2, user3));
		book2.getUsers().addAll(Arrays.asList(user1));
		book3.getUsers().addAll(Arrays.asList(user2));
		book4.getUsers().addAll(Arrays.asList(user3));
		book5.getUsers().addAll(Arrays.asList(user3));
		
		user1.addTrophy(trof1);
		user2.addTrophy(trof1);
		user3.addTrophy(trof1);
		
		trof1.getUsers().addAll(Arrays.asList(user1, user2, user3));
		
		admin.addRole(Role.ADMIN);
		
		userService.points(user1);
		userService.points(user2);
		userService.points(user3);
		userService.points(admin);
		
		bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5));
		trophyRepository.saveAll(Arrays.asList(trof1));
		userRepository.saveAll(Arrays.asList(user1, user2, user3, admin));
	}
}