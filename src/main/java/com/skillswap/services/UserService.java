package com.skillswap.services;

import org.springframework.http.ResponseEntity;

import com.skillswap.dto.LoginDTO;
import com.skillswap.dto.SignUpDTO;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<Object> signUpUser(@Valid SignUpDTO request);

	ResponseEntity<Object> loginUser(@Valid LoginDTO request);

}
