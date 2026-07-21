package com.skillswap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillswap.services.BrowseSkillsService;

@RestController
@RequestMapping("/rest/browse-skills")
public class BrowseSkillController {
	
	@Autowired
	private BrowseSkillsService browseSkillsService;
	
	@GetMapping
	public ResponseEntity<Object> getBrowseSkillsData(){
		return browseSkillsService.getBroseSkillData();
	}

}
