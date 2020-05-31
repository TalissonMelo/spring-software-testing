package com.talissonmelo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.talissonmelo.entity.User;
import com.talissonmelo.entity.dto.UserDTO;
import com.talissonmelo.repository.UserRepository;
import com.talissonmelo.service.exception.DataViolation;
import com.talissonmelo.service.exception.EntityNotFound;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFound("Usuário de ID: " + id + ", não encontrado."));
	}

	public User insert(User user) {
		return userRepository.save(user);
	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFound("Usuário de Id: não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolation("Deleção não permitida.");
		}
	}
	
	public User updateUser(Long id, User user) {
		User userSalve = findById(id);
		BeanUtils.copyProperties(user, userSalve, "id", "userRegister");
		return userRepository.save(userSalve);
	}
	
	public void existEmail(String email) {
		Boolean exist = userRepository.existsByEmail(email);	
		if(exist) {
			throw new EntityNotFound("Email já cadastrado.");
		}
	}
	
	public User logonEmailPassword(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		
		if(!user.getPassword().equals(userDTO.getPassword())) {
			throw new EntityNotFound("Senha incorreta.");
		}
		if(!user.getEmail().equals(userDTO.getEmail())) {
			throw new EntityNotFound("Email incorreto.");
		}
		
		return user;
	}
	
}
