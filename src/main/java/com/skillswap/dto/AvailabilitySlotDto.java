package com.skillswap.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilitySlotDto {
    private DayOfWeek day; // MONDAY, THURSDAY, etc.
    private LocalTime startTime; // e.g., 17:00 or 16:30
    private LocalTime endTime;   // e.g., 18:00 or 19:30

    public AvailabilitySlotDto(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		// TODO Auto-generated constructor stub
    	this.day = dayOfWeek;
    	this.startTime = startTime;
    	this.endTime = endTime;
	}
	// Getters and Setters
    public DayOfWeek getDay() { return day; }
	public void setDay(DayOfWeek day) { this.day = day; }

    public LocalTime getStartTime() { return startTime; }
	public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}