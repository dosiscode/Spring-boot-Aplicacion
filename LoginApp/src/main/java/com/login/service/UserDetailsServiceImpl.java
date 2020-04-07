package com.login.service;



import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.login.model.Role;
import com.login.repository.UserRepository;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.login.model.User appUser = userRepository.findAllByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username invalido"));
		
		Set grantList = new HashSet();
		for (Role roles : appUser.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles.getDescripcion());
			grantList.add(grantedAuthority);
		}
		UserDetails user = (UserDetails) new User(username, appUser.getPassword(), grantList);
		
		return user;
	}

	
	
}
