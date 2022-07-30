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
		
		User admin = new User(null, "Admin", passwordEncoder.encode("12345678"));
		admin.addRole(Role.ADMIN);
		
		User user1 = new User(null, "Matheus", passwordEncoder.encode("12345678"));
		User user2 = new User(null, "Robson", passwordEncoder.encode("12345678"));
		User user3 = new User(null, "Roger", passwordEncoder.encode("12345678"));
		
		// Visualizar Paginação Ranking 
		User user4 = new User(null, "user4", passwordEncoder.encode("12345678"));
		User user5 = new User(null, "user5", passwordEncoder.encode("12345678"));
		User user6 = new User(null, "user6", passwordEncoder.encode("12345678"));
		User user7 = new User(null, "user7", passwordEncoder.encode("12345678"));
		User user8 = new User(null, "user8", passwordEncoder.encode("12345678"));
		User user9 = new User(null, "user9", passwordEncoder.encode("12345678"));
		User user10 = new User(null, "user10", passwordEncoder.encode("12345678"));
		User user11 = new User(null, "user11", passwordEncoder.encode("12345678"));
		User user12 = new User(null, "user12", passwordEncoder.encode("12345678"));
		User user13 = new User(null, "user13", passwordEncoder.encode("12345678"));
		User user14 = new User(null, "user14", passwordEncoder.encode("12345678"));
		User user15 = new User(null, "user15", passwordEncoder.encode("12345678"));

		Trophy trof1 = new Trophy(null, "Leitor de Ação!", Genre.Acao);
		
		Book book1 = new Book(null, "Senhor dos Anéis", 1211, Genre.Fantasia, "https://images-na.ssl-images-amazon.com/images/I/71ZLavBjpRL.jpg");
		Book book2 = new Book(null, "Harry Potter e a pedra filosofal", 264, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/61jgm6ooXzL.jpg");
		Book book3 = new Book(null, "Harry Potter e a câmara secreta", 288, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/71NsVQ5MlwL.jpg");
		Book book4 = new Book(null, "Harry Potter e o prisioneiro de Azkaban", 348, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/81QnqHwRiUL.jpg");
		Book book5 = new Book(null, "Harry Potter e o cálice de fogo", 584, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/8172dLr8Z7L.jpg");
		Book book6 = new Book(null, "Harry Potter e a ordem da fênix", 704, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/81RI+iGwPGL.jpg");
		Book book7 = new Book(null, "Harry Potter e o enigma do príncipe", 472, Genre.Misterio, "https://images-na.ssl-images-amazon.com/images/I/81-jvnt+hgL.jpg");
		Book book8 = new Book(null, "Harry Potter e as reliquias da morte", 512, Genre.FiccaoCientifica, "https://images-na.ssl-images-amazon.com/images/I/811t1pfIZXL.jpg");
		Book book9 = new Book(null, "Death Note", 100, Genre.Quadrinhos, "http://pm1.narvii.com/6371/d435e76e753d92d55244e31adadde960b1c5e57a_00.jpg");
		
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
		
		user1.addTrophyManually(trof1);
		user2.addTrophyManually(trof1);
		user3.addTrophyManually(trof1);
		
		trof1.getUsers().addAll(Arrays.asList(user1, user2, user3));
		
		userService.points(user1);
		userService.points(user2);
		userService.points(user3);
		userService.points(admin);
		
		bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9));
		trophyRepository.saveAll(Arrays.asList(trof1));
		userRepository.saveAll(Arrays.asList(user1, user2, user3, admin, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13, user14, user15));
	}
}