package com.skillswap.services;

import org.springframework.http.ResponseEntity;

import com.skillswap.dto.SessionDTO;

import jakarta.validation.Valid;

public interface SessionService {

	ResponseEntity<Object> createSession(@Valid SessionDTO request);

	ResponseEntity<Object> acceptService(Long id);

	ResponseEntity<Object> cancelSession(Long id);

	ResponseEntity<Object> completeSession(Long id);

}
