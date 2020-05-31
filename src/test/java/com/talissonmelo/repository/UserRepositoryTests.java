package com.talissonmelo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTests {

	@Autowired
	UserRepository repository;
	
	@Test
	public void validationExistByEmail() {
		
		//Cenário
		User user = User.builder().email("talis@gmail.com").name("Talisson").password("123456").build();
		repository.save(user);
		
		//Ação & Execução
		boolean result = repository.existsByEmail("talis@gmail.com");
		
		//Verificação
		Assertions.assertTrue(result);
	}
}
