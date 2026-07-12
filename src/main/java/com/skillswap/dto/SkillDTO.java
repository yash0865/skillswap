package com.skillswap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SkillDTO {
	@NotBlank(message = "Skill cannot be empty")
	@Size(min = 3, max = 50, message = "Skill name must be between 3 and 50 characters")
	public String name;
	
	public String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
