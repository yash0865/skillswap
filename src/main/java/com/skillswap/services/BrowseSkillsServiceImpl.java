package com.skillswap.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.skillswap.dto.BrowseSkillResponse;
import com.skillswap.dto.ProfileResponse;
import com.skillswap.dto.SkillDTO;
import com.skillswap.entity.Review;
import com.skillswap.entity.Session;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import com.skillswap.security.CustomUserDetails;

@Service
public class BrowseSkillsServiceImpl implements BrowseSkillsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSkillRepository userSkillRepository;
	
	private User getUserFromContext() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	        throw new RuntimeException("No authenticated user found in security context");
	    }

	    Object principal = authentication.getPrincipal();

	    if (principal instanceof CustomUserDetails userDetails) {
	        // Option A: Fetch fresh entity from DB (Recommended for update/delete operations)
	        return userRepository.findById(userDetails.getUser().getId())
	                .orElseThrow(() -> new RuntimeException("User not found in database"));
	    }

	    throw new IllegalStateException("Unexpected principal type in SecurityContext: " + principal.getClass().getName());
	}

	@Override
	public ResponseEntity<Object> getBroseSkillData() {
		List<BrowseSkillResponse> data = new ArrayList<>();
		
		List<User> users = userRepository.findAll();
		
		User currentUser = getUserFromContext();
		
		for(User user : users) {
			if(user.getId().equals(currentUser.getId())){
				continue;
			}
			BrowseSkillResponse res = new BrowseSkillResponse();
			res.setId(user.getId());
			res.setName(user.getName());
			res.setLocation(user.getLocation());
			res.setRating(4.5f);
			
			List<UserSkill> userSkills = userSkillRepository.findByUserId(user.getId());
			List<SkillDTO> skillsRes = userSkills.stream()
			        .map(us -> new SkillDTO(us.getSkill().getName(), us.getType()))
			        .toList();
			
			res.setSkills(skillsRes);
			res.setImageURL(null);
			data.add(res);
		}
		
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getProfileDetails(Long id) {
		User user = getUserFromContext();
		
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
	}
	
	

}
