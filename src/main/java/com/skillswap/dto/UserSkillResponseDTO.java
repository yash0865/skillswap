package com.skillswap.dto;

import com.skillswap.entity.Skill;
import com.skillswap.utils.SkillType;

public class UserSkillResponseDTO {
	private Skill skill;
    private SkillType type;

    public UserSkillResponseDTO(Skill skill, SkillType skillType) {
        this.skill = skill;
        this.type = skillType;
    }

    // Getters and Setters
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public SkillType getType() { return type; }
    public void setType(SkillType type) { this.type = type; }
}
