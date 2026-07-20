package com.skillswap.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.skillswap.config.SecurityConfig;
import com.skillswap.dto.LoginDTO;
import com.skillswap.dto.LoginResponse;
import com.skillswap.dto.ProfileResponse;
import com.skillswap.dto.SignUpDTO;
import com.skillswap.dto.SkillDTO;
import com.skillswap.dto.UpdateDTO;
import com.skillswap.entity.Review;
import com.skillswap.entity.Session;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import com.skillswap.security.CustomUserDetails;
import com.skillswap.security.CustomUserDetailsService;
import com.skillswap.security.JWTService;
import com.skillswap.utils.SkillType;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserSkillRepository userSkillRepository;

	@Override
	public ResponseEntity<Object> signUpUser(SignUpDTO request) {
		try {
			Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		    if (existingUser.isPresent()) {
		        return ResponseEntity
		                .status(HttpStatus.BAD_REQUEST)
		                .body("Email already exists");
		    }

		    User user = new User();
		    user.setName(request.getName());
		    user.setEmail(request.getEmail());
		    user.setBio(request.getBio());

		    // Encode password
		    user.setPassword(passwordEncoder.encode(request.getPassword()));
		    user.setJoinedDate(new Date());

		    userRepository.save(user);

		    Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			String token = jwtService.generateToken(userDetails);

			return ResponseEntity
			        .status(HttpStatus.CREATED)
			        .body(new LoginResponse(
							token, 
							"Bearer", 
							userDetails.getUser().getName(), 
							userDetails.getUser().getEmail()));
		}catch (Exception e) {
			return new ResponseEntity<Object>("Something went wrong! Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	}
	
	@Override
	public ResponseEntity<Object> loginUser(@Valid LoginDTO request) {

		try {

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			String token = jwtService.generateToken(userDetails);

			return ResponseEntity.ok(new LoginResponse(
					token, 
					"Bearer", 
					userDetails.getUser().getName(), 
					userDetails.getUser().getEmail()));

		} catch (BadCredentialsException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
		}
	}

	@Override
	public ResponseEntity<Object> updateUser(UpdateDTO request) {
		try {
			Authentication authentication = SecurityContextHolder
			        .getContext()
			        .getAuthentication();

			CustomUserDetails userDetails =
			        (CustomUserDetails) authentication.getPrincipal();
			
			User user = userDetails.getUser();
			
			if (request.getName() != null && !request.getName().equals(user.getName())) {
				user.setName(request.getName());
			}
			if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
				user.setEmail(request.getEmail());
			}
			if (request.getBio() != null && !request.getBio().equals(user.getBio())) {
				user.setBio(request.getBio());
			}
			if (request.getLocation() != null && !request.getLocation().equals(user.getLocation())) {
				user.setLocation(request.getLocation());
			}
			if (request.getLinkedInURL() != null && !request.getLinkedInURL().equals(user.getLinkedInURL())) {
				user.setLinkedInURL(request.getLinkedInURL());
			}
			if (request.getPortfolio() != null && !request.getPortfolio().equals(user.getPortfolio())) {
				user.setPortfolio(request.getPortfolio());
			}
			
//			if (request.getPassword() != null
//					&& !securityConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
//				user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));
//			}
			userRepository.save(user);
			return new ResponseEntity<Object>("Fields Updated", HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<Object>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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

	@Override
	public ResponseEntity<Object> getUserProfile() {
		try {
			Authentication authentication = SecurityContextHolder
			        .getContext()
			        .getAuthentication();

			CustomUserDetails userDetails =
			        (CustomUserDetails) authentication.getPrincipal();
			
			User user = userDetails.getUser();
			
			List<UserSkill> userSkills = userSkillRepository.findByUserId(user.getId());
			List<SkillDTO> skills = new ArrayList<>();
			for(UserSkill skill : userSkills) {
				skills.add(new SkillDTO(skill.getSkill().getName(), skill.getType()));
			}
						
			List<Review> reviews = new ArrayList<>();
			
			List<Session> sessions = new ArrayList<>();
			
			return ResponseEntity.ok(new ProfileResponse(
					user.getName(), 
					user.getBio(), 
					user.getLocation(), 
					skills, 
					reviews, 
					sessions, 
					user.getJoinedDate(),
					user.getLinkedInURL(),
					user.getPortfolio()
					));
			
		}catch (Exception e) {
			return new ResponseEntity<Object>("Something went wrong! Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
