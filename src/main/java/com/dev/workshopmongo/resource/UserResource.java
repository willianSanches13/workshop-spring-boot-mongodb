package com.dev.workshopmongo.resource;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.workshopmongo.domain.Post;
import com.dev.workshopmongo.domain.User;
import com.dev.workshopmongo.dto.UserDto;
import com.dev.workshopmongo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll(){ // o ResponseEntity retorna uma requisição certinha com cabeçalho e tal 
		List<User> list = userService.findAll();
		List<UserDto> listDto = list.stream().map(x -> new UserDto(x)).toList(); 
		return ResponseEntity.ok().body(listDto); // status ok e no corpo das resposta a lista 
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable String id){
		User user = userService.findById(id).get();
		 return ResponseEntity.ok().body(new UserDto(user));
	}
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDto userDto){
		User user = userService.fromDto(userDto); 
		user = userService.insert(user); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		userService.delete(id); 
		 return ResponseEntity.noContent().build();
	}
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDto userDto, @PathVariable String id){
		User user = userService.fromDto(userDto); 
		user.setId(id);
		user = userService.update(user); 
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}/posts")
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
		User user = userService.findById(id).get();
		 return ResponseEntity.ok().body(user.getPosts());
	}
}















