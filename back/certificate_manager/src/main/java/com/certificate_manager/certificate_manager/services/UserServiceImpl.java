package com.certificate_manager.certificate_manager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.certificate_manager.certificate_manager.dtos.UserDTO;
import com.certificate_manager.certificate_manager.entities.User;
import com.certificate_manager.certificate_manager.enums.UserRole;
import com.certificate_manager.certificate_manager.exceptions.UserAlreadyExistsException;
import com.certificate_manager.certificate_manager.exceptions.UserNotFoundException;
import com.certificate_manager.certificate_manager.repositories.UserRepository;
import com.certificate_manager.certificate_manager.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService{
	
	@Autowired
	private UserRepository allUsers;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRole(UserRole.USER);
		allUsers.save(user);
		allUsers.flush();
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> ret = allUsers.findByEmail(email);
		System.out.println(email);
		System.out.println(allUsers.count());
		if (!ret.isEmpty()) {
			System.out.println(ret.get().getEmail());
			return org.springframework.security.core.userdetails.User.withUsername(email).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
		}
		throw new UsernameNotFoundException("User not found with this email: " + email);
	}
}
