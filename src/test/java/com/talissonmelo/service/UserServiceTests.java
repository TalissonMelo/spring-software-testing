package com.talissonmelo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.entity.User;
import com.talissonmelo.entity.dto.UserDTO;
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

	@Test
	public void logonAuthenticateUser() {
		// Cenário
		String email = "talis@gmail.com";
		String password = "12345";

		User user = User.builder().id(1l).name("Talisson").email(email).password(password).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(user);
		UserDTO dto = new UserDTO(email, password);

		// Ação - Execução
		User result = service.logonEmailPassword(dto);

		// Verificação
		Assertions.assertNotNull(result);

	}

	@Test
	public void errorAuthenticateUserEmail() {

		// Cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(null);
		UserDTO dto = new UserDTO(null, "12345");

		// Verificação
		Assertions.assertThrows(RuleException.class, () -> service.logonEmailPassword(dto));
	}

	@Test
	public void errorAuthenticateUserPassword() {

		// Cenário
		User user = User.builder().id(1l).name("Talisson").email("talis@gmail.com").password("12345").build();
		Mockito.when(repository.findByEmail("talis@gmail.com")).thenReturn(user);
		UserDTO dto = new UserDTO("talis@gmail.com", "123");

		// Verificação
		Assertions.assertThrows(RuleException.class, () -> service.logonEmailPassword(dto));
	}

}
