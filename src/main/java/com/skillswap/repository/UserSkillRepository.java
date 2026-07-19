package com.skillswap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillswap.entity.Skill;
import com.skillswap.entity.User;
import com.skillswap.entity.UserSkill;
import com.skillswap.utils.SkillType;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long>{
	List<UserSkill> findByUser(User user);

	Optional<UserSkill> findByUserAndSkill(Optional<User> user, Optional<Skill> skill);
	

	List<UserSkill> findByUserId(Long id);

	Optional<UserSkill> findByUserIdAndSkillName(Long id, String name);
}
