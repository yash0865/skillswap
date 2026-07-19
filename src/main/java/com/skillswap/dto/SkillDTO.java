package com.skillswap.dto;

import com.skillswap.utils.SkillType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SkillDTO {
	@NotBlank(message = "Skill cannot be empty")
	@Size(min = 3, max = 50, message = "Skill name must be between 3 and 50 characters")
	public String name;
	
	public SkillType type;
	
	public SkillDTO() {
		super();
	}

	public SkillDTO(
			@NotBlank(message = "Skill cannot be empty") @Size(min = 3, max = 50, message = "Skill name must be between 3 and 50 characters") String name,
			SkillType skillType) {
		super();
		this.name = name;
		this.type = skillType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SkillType getType() {
		return type;
	}

	public void setType(SkillType type) {
		this.type = type;
	}
}
