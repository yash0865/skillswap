package com.skillswap.services;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.skillswap.dto.AvailabilitySlotDto;
import com.skillswap.dto.BrowseSkillResponse;
import com.skillswap.dto.OnboardingDataDto;
import com.skillswap.dto.SkillDTO;
import com.skillswap.entity.AvailabilitySlot;
import com.skillswap.entity.Skill;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.AvailabilitySlotRepo;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;
import com.skillswap.security.CustomUserDetails;
import com.skillswap.utils.SkillType;

@Service
public class HomePageServiceImpl implements HomePageService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSkillRepository userSkillRepository;

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private AvailabilitySlotRepo availabilitySlotRepo;
	
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
	public ResponseEntity<Object> getUserBySkill(String skill) {
//		Optional<Skill> optionalSkill = skillRepository.findByNameIgnoreCase(skill);
//		if(!optionalSkill.isPresent()) {
//			return new ResponseEntity<Object>("Skill not Exists",HttpStatus.BAD_REQUEST);
//		}
		List<BrowseSkillResponse> responseList = new ArrayList<>();
		List<UserSkill>listOfUserSkill = userSkillRepository.findBySkillNameAndType(skill,SkillType.TEACH);
		for(UserSkill us : listOfUserSkill) {
			BrowseSkillResponse browseSkillResponse = new BrowseSkillResponse();
			browseSkillResponse.setId(us.getUser().getId());
			browseSkillResponse.setName(us.getUser().getName());
			browseSkillResponse.setRating(5);
			browseSkillResponse.setImageURL(us.getUser().getLinkedInURL());
			browseSkillResponse.setLocation(us.getUser().getLocation());
			
			List<SkillDTO> skills = userSkillRepository.findByUser(us.getUser())
					.stream()
					.map(u -> new SkillDTO(u.getSkill().getName(), u.getType()))
					.toList();
			
			browseSkillResponse.setSkills(skills);
			responseList.add(browseSkillResponse);
		}
		return new ResponseEntity<Object>(responseList,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> onboardUser(OnboardingDataDto req) {
		User user = getUserFromContext();
		user.setLocation(req.getLocation());
		user.setBio(req.getBio());
		user.setLinkedInURL(req.getLinkedin());
		user.setPortfolio(req.getPortfolio());
		user.setOccupation(req.getOccupation());
		user.setPhotoURL(req.getProfilePhotoUrl());
		
		List<AvailabilitySlot> availableSlots = new ArrayList<>();
		for(AvailabilitySlotDto slot : req.getAvailability()) {
			AvailabilitySlot availability = new AvailabilitySlot();
			availability.setDayOfWeek(slot.getDayOfWeek());
			availability.setStartTime(slot.getStartTime());
			availability.setEndTime(slot.getEndTime());
			availabilitySlotRepo.save(availability);
			availableSlots.add(availability);
		}
		
		for(SkillDTO skill : req.getSkills()) {
			Optional<Skill> optionalSkill = skillRepository.findByNameIgnoreCase(skill.getName());
			UserSkill userSkill = null;
			if(optionalSkill.isPresent()) {
				userSkill = new UserSkill(optionalSkill.get(), user, skill.getType());
			}else {
				Skill newSkill = new Skill(skill.getName());
				skillRepository.save(newSkill);
				userSkill = new UserSkill(newSkill, user, skill.getType());
			}
			userSkillRepository.save(userSkill);
		}
		
		user.setAvailableSlots(availableSlots);
		user.setSessionMode(req.getMode());
		userRepository.save(user);
		return new ResponseEntity<Object>("User Details Saved", HttpStatus.OK);
	}

}
