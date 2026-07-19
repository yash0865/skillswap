package com.skillswap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillswap.dto.LoginDTO;
import com.skillswap.dto.SignUpDTO;
import com.skillswap.dto.UpdateDTO;
import com.skillswap.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/rest/users")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signUpUser(@Valid @RequestBody SignUpDTO request){
		return userService.signUpUser(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginDTO request){
		return userService.loginUser(request);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable("id") Long id,@Valid @RequestBody UpdateDTO request) {
		return userService.updateUser(id,request);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id){
		return userService.deleteUser(id);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Object> getUserProfile(){
		return userService.getUserProfile();
	}
	
}
