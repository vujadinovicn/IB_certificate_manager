package com.certificate_manager.certificate_manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.enums.UserRole;
import com.certificate_manager.certificate_manager.exceptions.UserAlreadyExistsException;
import com.certificate_manager.certificate_manager.exceptions.UserNotFoundException;
import com.certificate_manager.certificate_manager.repositories.UserRepository;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository allUsers;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Override
	public User getUserByEmail(String email) {
		return allUsers.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
	}
	
	@Override
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
		if (this.doesUserExist(userDTO.getEmail()))
			throw new UserAlreadyExistsException();
		
		User user = new User(userDTO);
		user.setPassword(userDTO.getPassword());
		//user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRole(UserRole.USER);
		allUsers.save(user);
		allUsers.flush();
	}
}
