package com.login.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.login.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public Optional<User> findAllByUsername(String username);
	
}
