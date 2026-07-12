package com.skillswap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillswap.dto.SkillDTO;
import com.skillswap.services.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/skills")
@CrossOrigin("*")
public class SkillController {
	@Autowired
	private SkillService skillService;
	
	@PostMapping
	public ResponseEntity<Object> addSkill(@Valid @RequestBody SkillDTO request){
		return skillService.addSkill(request);
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllSkills(){
		return skillService.getAllSkills();
	}
	
	@PostMapping("/user/{id}")
	public ResponseEntity<Object> addUserSkill(@PathVariable("id") Long id, @RequestBody SkillDTO request){
		return skillService.addUserSkill(id, request);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUserSkill(@PathVariable("id") Long id){
		return skillService.getUserSkill(id);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteSkill(@PathVariable("id") Long id, @RequestBody SkillDTO request){
		return skillService.deleteUserSkill(id, request);
	}
}
