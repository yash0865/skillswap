package com.skillswap.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.skillswap.config.SecurityConfig;
import com.skillswap.dto.LoginDTO;
import com.skillswap.dto.LoginResponse;
import com.skillswap.dto.SignUpDTO;
import com.skillswap.dto.UpdateDTO;
import com.skillswap.entity.User;
import com.skillswap.repository.UserRepository;
import com.skillswap.security.CustomUserDetails;
import com.skillswap.security.CustomUserDetailsService;
import com.skillswap.security.JWTService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<Object> signUpUser(SignUpDTO request) {
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		if (existingUser.isPresent()) {
			return new ResponseEntity<Object>("Email Already Exists", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setEmail(request.getEmail());
		user.setBio(request.getBio());
		user.setName(request.getName());
//		user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<>("Signup successfull", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Object> loginUser(@Valid LoginDTO request) {

		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			String token = jwtService.generateToken(userDetails);

			return ResponseEntity.ok(new LoginResponse(token, "Bearer"));

		} catch (BadCredentialsException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
		}
	}

	@Override
	public ResponseEntity<Object> updateUser(Long id, UpdateDTO request) {
		Optional<User> existingUser = userRepository.findById(id);
		if (!existingUser.isPresent()) {
			return new ResponseEntity<Object>("User Does Not Exists", HttpStatus.BAD_REQUEST);
		}
		User user = existingUser.get();
		if (request.getName() != null && !user.getName().equals(request.getName())) {
			user.setName(request.getName());
		}
		if (request.getEmail() != null && !user.getEmail().equals(request.getEmail())) {
			user.setEmail(request.getEmail());
		}
		if (request.getBio() != null && !user.getBio().equals(request.getBio())) {
			user.setBio(request.getBio());
		}
//		if (request.getPassword() != null
//				&& !securityConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
//			user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
//		}
		userRepository.save(user);
		return new ResponseEntity<Object>("Fields Updated", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteUser(Long id) {
		Optional<User> existingUser = userRepository.findById(id);
		if (!existingUser.isPresent()) {
			return new ResponseEntity<Object>("User Does Not Exists", HttpStatus.BAD_REQUEST);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<Object>("User Deleted", HttpStatus.OK);
	}

}
