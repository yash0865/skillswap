package com.skillswap.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillswap.dto.SkillDTO;
import com.skillswap.dto.UserSkillResponseDTO;
import com.skillswap.entity.Skill;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import com.skillswap.utils.SkillType;

import jakarta.validation.Valid;

@Service
public class SkillServiceImpl implements SkillService {
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private UserSkillRepository userSkillRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public ResponseEntity<Object> addSkill(@Valid SkillDTO request) {
		Optional<Skill> existingSkill = skillRepo.findByNameIgnoreCase(request.getName());
		
		if(existingSkill.isPresent()) {
			return new ResponseEntity<Object>("Skill Already Exists", HttpStatus.OK);
		}
		
		Skill newSkill = new Skill();
		newSkill.setName(request.getName());
		skillRepo.save(newSkill);
		return new ResponseEntity<Object>("New Skill Added", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllSkills() {
		List<Skill> skills = skillRepo.findAll();
		if(skills.isEmpty()) {
			return new ResponseEntity<Object>("No Skills Available", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(skills, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getUserSkill(Long id) {
		Optional<User> user = userRepo.findById(id);		
		if(!user.isPresent()) {
			return new ResponseEntity<Object>("User not exists", HttpStatus.BAD_REQUEST);
		}
		List<UserSkill> userSkills = userSkillRepo.findByUser(user.get());
		if(userSkills.isEmpty()) {
			return new ResponseEntity<Object>("No Skills Available", HttpStatus.BAD_REQUEST);
		}
		
		// Map the entities into your clean DTO list
	    List<UserSkillResponseDTO> dtoList = userSkills.stream()
	        .map(us -> new UserSkillResponseDTO(us.getSkill(), us.getType()))
	        .collect(Collectors.toList());
	    
		return new ResponseEntity<Object>(dtoList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addUserSkill(Long id, SkillDTO request) {
		Optional<User> user = userRepo.findById(id);
		Optional<Skill> skill = skillRepo.findByNameIgnoreCase(request.getName());
		
		if(!user.isPresent()) {
			return new ResponseEntity<Object>("User not exists", HttpStatus.BAD_REQUEST);
		}
		
		if(!skill.isPresent()) {
			return new ResponseEntity<Object>("Skill does not exists", HttpStatus.BAD_REQUEST);
		}
		
		UserSkill newUserSkill = new UserSkill();
		newUserSkill.setSkill(skill.get());
		newUserSkill.setUser(user.get());
		if(request.getType().equalsIgnoreCase("TEACH")) {
			newUserSkill.setType(SkillType.TEACH);
		}else {
			newUserSkill.setType(SkillType.LEARN);
		}

		userSkillRepo.save(newUserSkill);
		return new ResponseEntity<Object>("New Skill Added", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteUserSkill(Long id, SkillDTO request) {
		Optional<User> user = userRepo.findById(id);
		Optional<Skill> skill = skillRepo.findByNameIgnoreCase(request.getName());
		
		if(!user.isPresent()) {
			return new ResponseEntity<Object>("User not exists", HttpStatus.BAD_REQUEST);
		}
		
		if(!skill.isPresent()) {
			return new ResponseEntity<Object>("Skill does not exists", HttpStatus.BAD_REQUEST);
		}
		
		Optional<UserSkill> userSkill = userSkillRepo.findByUserAndSkill(user, skill);	
		if(!userSkill.isPresent()) {
			return new ResponseEntity<Object>("User Skill does not exists", HttpStatus.BAD_REQUEST);
		}
		
		userSkillRepo.delete(userSkill.get());
		return new ResponseEntity<Object>("User Skill Deleted", HttpStatus.OK);
	}

}
