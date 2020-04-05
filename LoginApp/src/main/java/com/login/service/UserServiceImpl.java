package com.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.model.User;
import com.login.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}
	
	
	
	private boolean checkUsernameAvaible(User user) throws Exception {
		Optional<User> userFound = repository.findAllByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirm Password no coinciden");
		}
		
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		
		if (checkUsernameAvaible(user) && checkPasswordValid(user)) {
			user = repository.save(user);
		}
		
		return user;
	}
	

}
