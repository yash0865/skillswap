package com.skillswap.dto;

import java.util.List;
import java.util.Map;

import com.skillswap.entity.Skill;
import com.skillswap.entity.User;

public class BrowseSkillResponse {
	private Long id;
	private String name;
	private String imageURL;
	private String location;
	private float rating;
	private List<SkillDTO> skills;
	
	public BrowseSkillResponse(Long id, String name, String imageURL, String location, float rating,
			List<SkillDTO> skills) {
		super();
		this.id = id;
		this.name = name;
		this.imageURL = imageURL;
		this.location = location;
		this.rating = rating;
		this.skills = skills;
	}

	public BrowseSkillResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public List<SkillDTO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}
	
	
	
}
