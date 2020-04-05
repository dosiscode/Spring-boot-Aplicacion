package com.login.service;

import javax.validation.Valid;

import com.login.model.User;

public interface UserService {

	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	
}
