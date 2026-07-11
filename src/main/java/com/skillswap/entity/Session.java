package com.skillswap.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.ManyToAny;

import com.skillswap.utils.SessionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private User learner;

    @Column
    private LocalDateTime sessionDateTime;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public User getLearner() {
		return learner;
	}

	public void setLearner(User learner) {
		this.learner = learner;
	}

	public LocalDateTime getSessionDateTime() {
		return sessionDateTime;
	}

	public void setSessionDateTime(LocalDateTime sessionDateTime) {
		this.sessionDateTime = sessionDateTime;
	}

	public SessionStatus getStatus() {
		return status;
	}

	public void setStatus(SessionStatus status) {
		this.status = status;
	}
    
    
}