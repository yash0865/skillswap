package com.skillswap.services;

import org.springframework.http.ResponseEntity;

import com.skillswap.dto.OnboardingDataDto;

public interface HomePageService {

	ResponseEntity<Object> getUserBySkill(String skill);

	ResponseEntity<Object> onboardUser(OnboardingDataDto req);

}
