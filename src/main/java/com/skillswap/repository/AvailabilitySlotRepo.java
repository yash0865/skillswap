package com.skillswap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillswap.entity.AvailabilitySlot;

@Repository
public interface AvailabilitySlotRepo extends JpaRepository<AvailabilitySlot, Long>{

}
