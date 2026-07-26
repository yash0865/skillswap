package com.skillswap.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.skillswap.utils.SessionMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String location;
	
	@Column
	@Email
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String bio;
	
	@Column
	private Date joinedDate;
	
	@Column
	private String linkedInURL;
	
	@Column
	private String portfolio;
	
	@Column
	private String photoURL;
	
	@Column
	private String occupation;
	
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<AvailabilitySlot> availableSlots = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private SessionMode sessionMode;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getLinkedInURL() {
		return linkedInURL;
	}

	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public List<AvailabilitySlot> getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(List<AvailabilitySlot> availableSlots) {
		this.availableSlots = availableSlots;
	}

	public SessionMode getSessionMode() {
		return sessionMode;
	}

	public void setSessionMode(SessionMode sessionMode) {
		this.sessionMode = sessionMode;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	
}
