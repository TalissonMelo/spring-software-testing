package com.talissonmelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talissonmelo.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
	boolean existsByEmail(String email);
}
