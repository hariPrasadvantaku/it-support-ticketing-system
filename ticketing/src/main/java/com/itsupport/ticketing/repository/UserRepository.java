package com.itsupport.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
