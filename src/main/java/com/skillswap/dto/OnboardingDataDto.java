package com.skillswap.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.skillswap.utils.SessionMode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OnboardingDataDto {

    private String profilePhotoUrl;
    private String coverPhotoUrl;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    @NotNull
    private List<SkillDTO> skills;
    @NotNull
    private List<AvailabilitySlotDto> availability;

    @NotNull(message = "Mode is required")
    private SessionMode mode;

    private String linkedin;
    private String github;
    private String portfolio;
    private String instagram;
    
	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}
	public void setProfilePhotoUrl(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}
	public String getCoverPhotoUrl() {
		return coverPhotoUrl;
	}
	public void setCoverPhotoUrl(String coverPhotoUrl) {
		this.coverPhotoUrl = coverPhotoUrl;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public List<SkillDTO> getSkills() {
		return skills;
	}
	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}
	public List<AvailabilitySlotDto> getAvailability() {
		return availability;
	}
	public void setAvailability(List<AvailabilitySlotDto> availability) {
		this.availability = availability;
	}
	public SessionMode getMode() {
		return mode;
	}
	public void setMode(SessionMode mode) {
		this.mode = mode;
	}
	public String getLinkedin() {
		return linkedin;
	}
	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
}