package com.skillswap.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillswap.config.SecurityConfig;
import com.skillswap.dto.LoginDTO;
import com.skillswap.dto.SignUpDTO;
import com.skillswap.dto.UpdateDTO;
import com.skillswap.entity.User;
import com.skillswap.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityConfig securityConfig;

	@Override
	public ResponseEntity<Object> signUpUser(SignUpDTO request) {
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
		
		if(existingUser.isPresent()) {
			return new ResponseEntity<Object>("Email Already Exists", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setBio(request.getBio());
		user.setName(request.getName());
		user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<>("Signup successfull", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Object> loginUser(@Valid LoginDTO request) {
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
		
		if(!existingUser.isPresent()) {
			return new ResponseEntity<Object>("Email Not Exists", HttpStatus.BAD_REQUEST);
		}
		
		User user = existingUser.get();
		
		if(!securityConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
			return new ResponseEntity<Object>("Incorrect Password", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Object>("Login Successfull", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateUser(Long id, UpdateDTO request) {
		Optional<User> existingUser = userRepository.findById(id);
		if(!existingUser.isPresent()) {
			return new ResponseEntity<Object>("User Does Not Exists", HttpStatus.BAD_REQUEST);
		}
		User user = existingUser.get();
		if(request.getName()!= null && !user.getName().equals(request.getName())) {
			user.setName(request.getName());
		}
		if(request.getEmail() != null && !user.getEmail().equals(request.getEmail())) {
			user.setEmail(request.getEmail());
		}
		if(request.getBio() != null && !user.getBio().equals(request.getBio())) {
			user.setBio(request.getBio());
		}
		if(request.getPassword() != null && !securityConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
			user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
		}
		userRepository.save(user);
		return new ResponseEntity<Object>("Fields Updated", HttpStatus.OK);
	}

}
