package com.skillswap.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillswap.entity.AvailabilitySlot;
import com.skillswap.entity.User;

@Repository
public interface AvailabilitySlotRepo extends JpaRepository<AvailabilitySlot, Long>{
	Set<AvailabilitySlot> findByUser_Id(Long userId);
}
