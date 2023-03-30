package com.certificate_manager.certificate_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.exceptions.UserNotFoundException;
import com.certificate_manager.certificate_manager.repositories.UserRepository;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository allUsers;
	
	public User getUserByEmail(String email) {
		return allUsers.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
	}
	
	public boolean doesUserExist(String email) {
		try {
			this.getUserByEmail(email);
			return true;
		}
		catch (UserNotFoundException e){
			return false;
		}
	}
	
	@Override
	public void register(UserDTO userDTO) {
		// TODO Auto-generated method stub
	}
}
