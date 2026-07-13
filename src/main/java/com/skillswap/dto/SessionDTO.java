package com.skillswap.dto;

import java.time.LocalDateTime;
import com.skillswap.utils.SessionStatus;
import jakarta.validation.constraints.NotNull;

public class SessionDTO {
	@NotNull
    private Long teacherId;
	@NotNull
    private Long learnerId;
	@NotNull
    private LocalDateTime sessionDateTime;
	
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Long getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(Long learnerId) {
		this.learnerId = learnerId;
	}
	public LocalDateTime getSessionDateTime() {
		return sessionDateTime;
	}
	public void setSessionDateTime(LocalDateTime sessionDateTime) {
		this.sessionDateTime = sessionDateTime;
	}
}
