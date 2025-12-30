package com.itsupport.ticketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public User registerUser(User user) {
		User exitingUser = userRepository.findByEmail(user.getEmail());
		if(exitingUser != null) {
			throw new RuntimeException("Email already registered");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRole("ROLE_USER");
		userRepository.save(user);
		return null;
	}

	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		if(user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
	
}
