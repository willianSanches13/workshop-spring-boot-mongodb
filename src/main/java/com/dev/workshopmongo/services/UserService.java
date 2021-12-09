package com.dev.workshopmongo.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dev.workshopmongo.domain.User;
import com.dev.workshopmongo.dto.UserDto;
import com.dev.workshopmongo.repository.UserRepository;
import com.dev.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}
	

	
	public Optional<User> findById(String id) {
		return Optional.of(userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"))); 
	}
	
	public User insert(User user) {
		return userRepository.insert(user);
	}
	public void delete(String id) {
		findById(id); // chamando o find by id primeiro, se ele não encontrar ele lança a exceção
		userRepository.deleteById(id);
	}
	public User update(User user) {
		User newUser = userRepository.findById(user.getId()).get(); 
		updateData(newUser, user); 
		return userRepository.save(newUser);
	}
	public void updateData(User newUser, User user) {
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
	}
	public User fromDto(UserDto objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail()); 
	}
}
