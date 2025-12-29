package com.itsupport.ticketing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		user.setRole("USER");
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
