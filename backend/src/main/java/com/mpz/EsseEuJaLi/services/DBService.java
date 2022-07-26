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
		
		// Ver Paginação
		User user4 = new User(null, "user4", passwordEncoder.encode("123"));
		User user5 = new User(null, "user5", passwordEncoder.encode("123"));
		User user6 = new User(null, "user6", passwordEncoder.encode("123"));
		User user7 = new User(null, "user7", passwordEncoder.encode("123"));
		User user8 = new User(null, "user8", passwordEncoder.encode("123"));
		User user9 = new User(null, "user9", passwordEncoder.encode("123"));
		User user10 = new User(null, "user10", passwordEncoder.encode("123"));
		User user11 = new User(null, "user11", passwordEncoder.encode("123"));
		User user12 = new User(null, "user12", passwordEncoder.encode("123"));
		User user13 = new User(null, "user13", passwordEncoder.encode("123"));
		User user14 = new User(null, "user14", passwordEncoder.encode("123"));
		User user15 = new User(null, "user15", passwordEncoder.encode("123"));
		
		Trophy trof1 = new Trophy(null, "Leitor de Ação!", Genre.Acao);
		
		Book book1 = new Book(null, "Livro 1", 500, Genre.Acao, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book2 = new Book(null, "Livro 2", 100, Genre.Aventura, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book3 = new Book(null, "Livro 3", 150, Genre.Biografia, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book4 = new Book(null, "Livro 4", 99, Genre.Terror, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book5 = new Book(null, "Livro 5", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book6 = new Book(null, "Livro 5.1", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book7 = new Book(null, "Livro 5.2", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book8 = new Book(null, "Livro 5.3", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		Book book9 = new Book(null, "Livro 5.4", 50, Genre.FiccaoCientifica, "https://www.redeicm.org.br/carmo/wp-content/uploads/sites/5/2019/01/Livro.jpg");
		
		user1.getBooks().addAll(Arrays.asList(book1, book2));
		user2.getBooks().addAll(Arrays.asList(book1, book3));
		user3.getBooks().addAll(Arrays.asList(book4, book1, book5));
		
		admin.getBooks().addAll(Arrays.asList(book5, book6, book7, book8));
		
		book1.getUsers().addAll(Arrays.asList(user1, user2, user3));
		book2.getUsers().addAll(Arrays.asList(user1));
		book3.getUsers().addAll(Arrays.asList(user2));
		book4.getUsers().addAll(Arrays.asList(user3));
		book5.getUsers().addAll(Arrays.asList(user3, admin));
		
		book6.getUsers().addAll(Arrays.asList(admin));
		book7.getUsers().addAll(Arrays.asList(admin));
		book8.getUsers().addAll(Arrays.asList(admin));
		//book9.getUsers().addAll(Arrays.asList(admin));
		
		user1.addTrophyManually(trof1);
		user2.addTrophyManually(trof1);
		user3.addTrophyManually(trof1);
		
		trof1.getUsers().addAll(Arrays.asList(user1, user2, user3));
		
		admin.addRole(Role.ADMIN);
		
		userService.points(user1);
		userService.points(user2);
		userService.points(user3);
		userService.points(admin);
		
		bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9));
		trophyRepository.saveAll(Arrays.asList(trof1));
		userRepository.saveAll(Arrays.asList(user1, user2, user3, admin, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13, user14, user15));
	}
}