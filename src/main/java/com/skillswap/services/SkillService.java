package com.skillswap.services;

import org.springframework.http.ResponseEntity;

import com.skillswap.dto.SkillDTO;

import jakarta.validation.Valid;

public interface SkillService {

	ResponseEntity<Object> addSkill(@Valid SkillDTO request);

	ResponseEntity<Object> getAllSkills();

	ResponseEntity<Object> getUserSkill(Long id);

	ResponseEntity<Object> addUserSkill(Long id, SkillDTO request);

	ResponseEntity<Object> deleteUserSkill(Long id, SkillDTO request);

}
