package com.talissonmelo.resource;

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

import com.talissonmelo.entity.User;
import com.talissonmelo.entity.dto.UserDTO;
import com.talissonmelo.repository.UserRepository;
import com.talissonmelo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user) {
		service.existEmail(user.getEmail());
		user = service.insert(user);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(Long id){
		service.findById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
		user = service.updateUser(id, user);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping(value = "/logon")
	public ResponseEntity<User> logon (@PathVariable UserDTO userDTO){
		User user = service.logonEmailPassword(userDTO);
		return ResponseEntity.ok().body(user);
	}
}
