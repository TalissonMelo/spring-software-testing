package com.talissonmelo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.repository.UserRepository;
import com.talissonmelo.service.exception.RuleException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@Autowired
	UserService service;

	@MockBean
	UserRepository repository;

	@Test
	public void validationByEmailSuccess() {

		// Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// Ação - Execução
		service.existEmail("talis@gmail.com");
	}
	
	@Test
	public void validationByEmailError() {

		// Cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		// Ação - Execução
		Assertions.assertThrows(RuleException.class, () -> service.existEmail("talis@gmail.com"));
	}

}
