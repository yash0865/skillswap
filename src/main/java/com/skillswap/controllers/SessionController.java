package com.skillswap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillswap.dto.SessionDTO;
import com.skillswap.services.SessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/session")
@CrossOrigin("*")
public class SessionController {

	@Autowired
	private SessionService sessionService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createSession(@Valid @RequestBody SessionDTO request){
		return sessionService.createSession(request);
	}
	
	@PostMapping("/accept/{id}")
	public ResponseEntity<Object> acceptSession(@PathVariable("id") Long id){
		return sessionService.acceptService(id);
	}
	
	@PostMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelSession(@PathVariable("id") Long id){
		return sessionService.cancelSession(id);
	}
	
	@PostMapping("/complete/{id}")
	public ResponseEntity<Object> completeSession(@PathVariable("id") Long id){
		return sessionService.completeSession(id);
	}
}
