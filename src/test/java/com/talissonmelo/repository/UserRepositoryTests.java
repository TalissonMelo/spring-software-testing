package com.talissonmelo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE) utilizado para não sobrescrever as configurações do banco de dados
public class UserRepositoryTests {

	@Autowired
	UserRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void validationExistByEmail() {

		// Cenário
		User user = createUser();
		entityManager.persist(user);

		// Ação & Execução
		boolean result = repository.existsByEmail("talis@gmail.com");

		// Verificação
		Assertions.assertTrue(result);
	}

	@Test
	public void ReturnFalseExistByEmail() {

		// Cenário
		// repository.deleteAll();

		// Ação & Execução
		boolean result = repository.existsByEmail("talis@gmail.com");

		// Verificação
		Assertions.assertFalse(result);
	}

	@Test
	public void PersistUserInDataBase() {
		// Cenário
		User user = createUser();

		// Ação & Execução
		User userSalve = repository.save(user);

		// Verificação
		Assertions.assertNotNull(userSalve.getId());
	}
	
	@Test
	public void FindByUserWhithEmailSuccess() {
		// Cenário
		User user = createUser();
		entityManager.persist(user);

		// Ação & Execução
		User userEmail = repository.findByEmail("talis@gmail.com");
		
		// Verificação
		Assertions.assertNotNull(userEmail);
	}
	
	@Test
	public void FindByNullEmailError() {

		// Ação & Execução
		User userEmail = repository.findByEmail("talis@gmail.com");
		
		// Verificação
		Assertions.assertNull(userEmail);
	}
	
	public static User createUser() {
		return User.builder().name("Talisson").email("talis@gmail.com").password("12345").build();
	}
}
