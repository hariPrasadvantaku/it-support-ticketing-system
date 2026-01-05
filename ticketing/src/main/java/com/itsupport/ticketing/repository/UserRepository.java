package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	List<User> findByRole(String role);

}
