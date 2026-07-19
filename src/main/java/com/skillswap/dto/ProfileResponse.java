package com.skillswap.dto;

import java.util.Date;
import java.util.List;

import com.skillswap.entity.Review;
import com.skillswap.entity.Session;
import com.skillswap.entity.Skill;
import com.skillswap.entity.UserSkill;

public class ProfileResponse {
	private String userName;
	private String userBio;
	private String userLocation;
	private List<SkillDTO> skills;
	private List<Review> userReviews;
	private List<Session> userSessions;
	private Date memberSince;
	
	public ProfileResponse(String userName, String userBio, String userLocation, List<SkillDTO> skills,
			List<Review> userReviews, List<Session> userSessions, Date memberSince) {
		super();
		this.userName = userName;
		this.userBio = userBio;
		this.userLocation = userLocation;
		this.skills = skills;
		this.userReviews = userReviews;
		this.userSessions = userSessions;
		this.memberSince = memberSince;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserBio() {
		return userBio;
	}
	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public List<SkillDTO> getSkills() {
		return skills;
	}
	public void setSkills(List<SkillDTO> skills) {
		this.skills = skills;
	}
	public List<Review> getUserReviews() {
		return userReviews;
	}
	public void setUserReviews(List<Review> userReviews) {
		this.userReviews = userReviews;
	}
	public List<Session> getUserSessions() {
		return userSessions;
	}
	public void setUserSessions(List<Session> userSessions) {
		this.userSessions = userSessions;
	}
	public Date getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}
	
	
}
