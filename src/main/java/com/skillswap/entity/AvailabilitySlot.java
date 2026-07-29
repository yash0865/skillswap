package com.skillswap.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "availability_slots")
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day; // MONDAY, TUESDAY, etc.

    private LocalTime startTime;
    private LocalTime endTime;
    
	public DayOfWeek getDayOfWeek() {
		return day;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.day = dayOfWeek;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
    
    
}