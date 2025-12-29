package com.itsupport.ticketing.service;

import com.itsupport.ticketing.entity.User;

public interface UserService {
	User registerUser(User user);
	User login(String email,String password);
}
