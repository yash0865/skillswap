package com.skillswap.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AvailabilitySlotDto {
    private DayOfWeek dayOfWeek; // MONDAY, THURSDAY, etc.
    private LocalTime startTime; // e.g., 17:00 or 16:30
    private LocalTime endTime;   // e.g., 18:00 or 19:30

    // Getters and Setters
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}