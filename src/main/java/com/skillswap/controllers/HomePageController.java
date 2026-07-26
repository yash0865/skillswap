package com.skillswap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillswap.dto.OnboardingDataDto;
import com.skillswap.services.HomePageService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/rest/homepage")
public class HomePageController {
	
	@Autowired
	private HomePageService homePageService;
	
	@PostMapping
	public ResponseEntity<Object> getUserBySkill(@RequestParam("skill") String skill){
		return homePageService.getUserBySkill(skill);
	}
	
	@PostMapping("/onboard")
	public ResponseEntity<Object> onboardUser(@RequestBody OnboardingDataDto req) {
		return homePageService.onboardUser(req);
	}
	

}
