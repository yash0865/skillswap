package com.skillswap.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.skillswap.dto.SkillDTO;
import com.skillswap.dto.UserSkillResponseDTO;
import com.skillswap.entity.Skill;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import com.skillswap.security.CustomUserDetails;
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
		
//		Skill newSkill = new Skill();
//		newSkill.setName(request.getName());
//		skillRepo.save(newSkill);
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
	public ResponseEntity<Object> addUserSkill(SkillDTO request) {
		Authentication authentication = SecurityContextHolder
		        .getContext()
		        .getAuthentication();

		CustomUserDetails userDetails =
		        (CustomUserDetails) authentication.getPrincipal();
		
		User user = userDetails.getUser();
		
		Optional<Skill> optionalSkill = skillRepo.findByNameIgnoreCase(request.getName());
		Skill skill = null;
		if(optionalSkill.isPresent()) {
			skill = optionalSkill.get();
		}else {
			skill = new Skill(request.getName());
			skillRepo.save(skill);
		}
		
		UserSkill newUserSkill = new UserSkill();
		newUserSkill.setSkill(skill);
		newUserSkill.setUser(user);
		if(request.getType().equals(SkillType.TEACH)) {
			newUserSkill.setType(SkillType.TEACH);
		}else {
			newUserSkill.setType(SkillType.LEARN);
		}

		userSkillRepo.save(newUserSkill);
		return new ResponseEntity<Object>("New Skill Added", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> deleteUserSkill(SkillDTO request) {
		Authentication authentication = SecurityContextHolder
		        .getContext()
		        .getAuthentication();

		CustomUserDetails userDetails =
		        (CustomUserDetails) authentication.getPrincipal();
		
		User user = userDetails.getUser();
		
		Optional<UserSkill> optionSkill = userSkillRepo.findByUserIdAndSkillName(user.getId(), request.getName());
		
		if(!optionSkill.isPresent()) {
			return new ResponseEntity<Object>("Skill does not exist", HttpStatus.BAD_REQUEST);
		}
		
		userSkillRepo.delete(optionSkill.get());
		
		return new ResponseEntity<Object>("Skill delted", HttpStatus.OK);
	}

}
