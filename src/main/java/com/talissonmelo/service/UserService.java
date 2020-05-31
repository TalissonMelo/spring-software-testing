package com.talissonmelo.service;

import java.time.LocalDateTime;

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
import com.talissonmelo.service.exception.RuleException;

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
		userSalve.setUserUpdate(LocalDateTime.now());
		return userRepository.save(userSalve);
	}

	public void existEmail(String email) {
		Boolean exist = userRepository.existsByEmail(email);
		if (exist) {
			throw new RuleException("Email já cadastrado.");
		}
	}

	public User logonEmailPassword(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());

		if (user == null) {
			throw new RuleException("Email incorreto.");
		}

		if (!user.getPassword().equals(userDTO.getPassword())) {
			throw new RuleException("Senha incorreta.");
		}

		return user;
	}

}
