package com.talissonmelo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.entity.User;
import com.talissonmelo.repository.UserRepository;
import com.talissonmelo.service.exception.RuleException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repository;

	@Test
	public void validationByEmailSuccess() {

		// Cenário
		repository.deleteAll();

		// Ação - Execução
		service.existEmail("talis@gmail.com");
	}
	
	@Test
	public void validationByEmailError() {

		// Cenário
		User user = User.builder().name("Talisson").email("talis@gmail.com").password("123456").build();
		repository.save(user);
		
		// Ação - Execução
		Assertions.assertThrows(RuleException.class, () -> service.existEmail("talis@gmail.com"));
	}

}
