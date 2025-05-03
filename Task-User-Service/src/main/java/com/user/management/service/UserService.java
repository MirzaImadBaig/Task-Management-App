package com.user.management.service;

import java.util.List;

import com.user.management.exception.UserException;
import com.user.management.model.User;




public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;
	
	public User findUserById(Long userId) throws UserException;

	public List<User> findAllUsers();
}
