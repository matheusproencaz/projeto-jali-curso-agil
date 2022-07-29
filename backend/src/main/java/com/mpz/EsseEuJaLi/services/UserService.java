package com.mpz.EsseEuJaLi.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpz.EsseEuJaLi.model.Book;
import com.mpz.EsseEuJaLi.model.Trophy;
import com.mpz.EsseEuJaLi.model.User;
import com.mpz.EsseEuJaLi.model.dto.UserLoginDTO;
import com.mpz.EsseEuJaLi.model.dto.UserUpdatePasswordDTO;
import com.mpz.EsseEuJaLi.model.enums.Genre;
import com.mpz.EsseEuJaLi.model.enums.Role;
import com.mpz.EsseEuJaLi.repositories.UserRepository;
import com.mpz.EsseEuJaLi.security.UserSS;
import com.mpz.EsseEuJaLi.services.exceptions.AlreadyAdded;
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
		if (user == null || !user.hasRole(Role.ADMIN) && !name.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado!");
		}

		User obj = userRepository.findByName(user.getUsername());

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Usuário não encontrado! id: " + user.getId() + ", tipo: " + User.class.getName());
		}
		return obj;
	}

	public User updateUser(UserUpdatePasswordDTO objDTO) {
		User newObj = findUserById(objDTO.getId());
		newObj.setName(objDTO.getUserName());
		newObj.setPassword(pwEncoder.encode(objDTO.getNewPassword()));
		return userRepository.save(newObj);
	}
	
	@Transactional
	public User insertUser(User obj) {
		obj = userRepository.save(obj);
		return obj;
	}

	public void addBook(Long idUser, Long idBook) {
		User user = findUserById(idUser);
		Book book = bookService.findBook(idBook);

		if (user.getBooks().contains(book)) {
			throw new AlreadyAdded("Livro já adicionado ao usuário!");
		}
		user.addBooks(book);

		points(user);
		addTrophyByReading(user);
		user = userRepository.save(user);
	}

	public void removeBook(Long idUser, Long idBook) {
		User user = findUserById(idUser);
		Book book = bookService.findBook(idBook);

		if (!user.getBooks().contains(book)) {
			throw new ObjectNotFoundException("Este usuário não tem esse livro!");
		}

		user.removeBook(book);
		points(user);
		removeTrophyWhenBookIsRemoved(user);
		userRepository.save(user);
	}

	public void addTrophy(Long idUser, Long idTrophy) {
		User user = findUserById(idUser);
		Trophy trof = trophyService.findTrophy(idTrophy);

		if (user.getTrophies().contains(trof)) {
			throw new AlreadyAdded("Troféu já adicionado ao usuário!");
		}

		user.addTrophyManually(trof);
		user = userRepository.save(user);
	}

	public void removeTrophy(Long idUser, Long idTrophy) {
		User user = findUserById(idUser);
		Trophy trof = trophyService.findTrophy(idTrophy);
		user.removeTrophy(trof);
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
		int pages = user.getBooks().stream().map(p -> p.getPages()).reduce(0,
				(subtotal, bookPages) -> subtotal + bookPages);

		if (pages == 0) {
			user.setPoints(0);
			return;
		}

		// https://stackoverflow.com/questions/7139382/java-rounding-up-to-an-int-using-math-ceil
		int points = (pages + 100 - 1) / 100;
		user.setPoints(points);
	}

	private void addTrophyByGenreBook(String nameTrophy, User user, Genre g) {
		Trophy trophy = trophyService.findTrophyByName(nameTrophy);

		if (trophy != null) {
			user.addTrophyManually(trophy);
		} else {
			Trophy newTrophy = new Trophy(null, nameTrophy, g);
			trophyService.insertTrophy(newTrophy);
			user.addTrophyManually(newTrophy);
		}
	}

	private void addTrophyByReading(User user) {

		Set<Book> books = user.getBooks();

		for (Genre g : Genre.values()) {
			Set<Book> booksByGenre = books.stream().filter(b -> b.getGenre().equals(g)).collect(Collectors.toSet());

			if (booksByGenre.size() >= 1) {
				String nameTrophy = "Troféu leitor novato de " + g.getDescription();
				addTrophyByGenreBook(nameTrophy, user, g);
			} else {
				continue;
			}

			if (booksByGenre.size() >= 5) {
				String nameTrophy = "Troféu leitor médio de " + g.getDescription();
				addTrophyByGenreBook(nameTrophy, user, g);
			}

			if (booksByGenre.size() >= 10) {
				String nameTrophy = "Troféu amante de livros de " + g.getDescription();
				addTrophyByGenreBook(nameTrophy, user, g);
			}

			if (booksByGenre.size() >= 15) {
				String nameTrophy = "Troféu leitor viciado em " + g.getDescription();
				addTrophyByGenreBook(nameTrophy, user, g);
			}
		}
		userRepository.save(user);
	}

	private void removeTrophyWhenBookIsRemoved(User user) {

		Set<Book> books = user.getBooks();

		for (Genre g : Genre.values()) {
			Set<Book> booksByGenre = books.stream().filter(b -> b.getGenre().equals(g)).collect(Collectors.toSet());

			if (booksByGenre.size() > 15) {
				continue;
			}
			
			if (booksByGenre.size() < 1) {
				Trophy t1 = trophyService.findTrophyByName("Troféu leitor novato de " + g.getDescription());
				if(t1 != null)
					removeTrophy(user.getId(), t1.getId());
			}

			if (booksByGenre.size() < 5) {
				Trophy t5 = trophyService.findTrophyByName("Troféu leitor médio de " + g.getDescription());
				
				if(t5 != null) 
					removeTrophy(user.getId(), t5.getId());
			}

			if (booksByGenre.size() < 10) {
				Trophy t10 = trophyService.findTrophyByName("Troféu amante de livros de " + g.getDescription());
				
				if(t10 != null)
					removeTrophy(user.getId(), t10.getId());
			}

			if (booksByGenre.size() < 15) {
				Trophy t15 = trophyService.findTrophyByName("Troféu leitor viciado em " + g.getDescription());
				if(t15 != null)
					removeTrophy(user.getId(), t15.getId());
			}
			
			userRepository.save(user);
		}
	}
}
