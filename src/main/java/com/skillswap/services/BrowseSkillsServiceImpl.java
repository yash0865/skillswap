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
import org.springframework.stereotype.Service;

import com.skillswap.dto.BrowseSkillResponse;
import com.skillswap.dto.SkillDTO;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.repository.UserSkillRepository;

@Service
public class BrowseSkillsServiceImpl implements BrowseSkillsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSkillRepository userSkillRepository;
	
	@Autowired
	private SkillRepository skillRepository;

	@Override
	public ResponseEntity<Object> getBroseSkillData() {
		List<BrowseSkillResponse> data = new ArrayList<>();
		
		List<User> users = userRepository.findAll();
		
		for(User user : users) {
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

}
