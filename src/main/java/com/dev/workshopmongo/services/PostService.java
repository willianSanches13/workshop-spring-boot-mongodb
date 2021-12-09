package com.dev.workshopmongo.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.workshopmongo.domain.Post;
import com.dev.workshopmongo.repository.PostRepository;
import com.dev.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;

	
	public Optional<Post> findById(String id) {
		return Optional.of(postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"))); 
	}
	
	public List<Post> findByTitle(String text){
		return postRepository.findByTitleContainingIgnoreCase(text);
	}
}