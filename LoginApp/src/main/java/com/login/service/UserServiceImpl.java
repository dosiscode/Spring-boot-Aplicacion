package com.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.login.dto.ChangePasswordForm;
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
		
		if (user.getConfirmPassword()== null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirm Password es boligatorio");
		}
		
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



	@Override
	public User getUserById(Long id) throws Exception {
		
		return repository.findById(id).orElseThrow(()-> new Exception("El usuario no existe"));
	}



	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public User updateUser(User fromUser) throws Exception {
	
		User toUser = getUserById(fromUser.getId());
		
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}
	
	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstname1(from.getFirstname1());
		to.setLastname(from.getLastname());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}



	@Override
	public void deleteUser(Long id) throws Exception {
		
		User user = getUserById(id);
		repository.delete(user);
		
	}



	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		
		User user = getUserById(form.getId());
		
		if (!user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Password actual invalida");
		}
		
		if (user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("La nueva password debe ser diferente al password actual");
		}
		
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("La nueva password y la confirmación no coinciden");
		}
		
		user.setPassword(form.getNewPassword());
		return repository.save(user);
	}
	

}
