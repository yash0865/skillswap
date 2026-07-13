package com.skillswap.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillswap.dto.SessionDTO;
import com.skillswap.entity.Session;
import com.skillswap.entity.User;
import com.skillswap.repository.SessionRepository;
import com.skillswap.repository.UserRepository;
import com.skillswap.utils.SessionStatus;

import jakarta.validation.Valid;

@Service
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionRepository sessionRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public ResponseEntity<Object> createSession(@Valid SessionDTO request) {
		Optional<User> teacherUser = userRepo.findById(request.getTeacherId());
		Optional<User> learnerUser = userRepo.findById(request.getLearnerId());
		
		if(!teacherUser.isPresent() || !learnerUser.isPresent()) {
			return new ResponseEntity<Object>("Users not found", HttpStatus.BAD_REQUEST);
		}
		
		Session newSession = new Session();
		newSession.setTeacher(teacherUser.get());
		newSession.setLearner(learnerUser.get());
		newSession.setSessionDateTime(request.getSessionDateTime());
		newSession.setStatus(SessionStatus.REQUESTED);
		sessionRepo.save(newSession);
		return new ResponseEntity<Object>("New Session Created", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> acceptService(Long id) {
		Optional<Session> session = sessionRepo.findById(id);
		
		if(!session.isPresent()) {
			return new ResponseEntity<Object>("Session Not Exists", HttpStatus.BAD_REQUEST);
		}
		
		Session savedSession = session.get();
		savedSession.setStatus(SessionStatus.ACCEPTED);
		sessionRepo.save(savedSession);
		return new ResponseEntity<Object>("Session Accepted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> cancelSession(Long id) {
		Optional<Session> session = sessionRepo.findById(id);
		
		if(!session.isPresent()) {
			return new ResponseEntity<Object>("Session Not Exists", HttpStatus.BAD_REQUEST);
		}
		Session savedSession = session.get();
		savedSession.setStatus(SessionStatus.CANCELLED);
		sessionRepo.save(savedSession);
		return new ResponseEntity<Object>("Session Cancelled", HttpStatus.OK);	
	}

	@Override
	public ResponseEntity<Object> completeSession(Long id) {
		Optional<Session> session = sessionRepo.findById(id);
		
		if(!session.isPresent()) {
			return new ResponseEntity<Object>("Session Not Exists", HttpStatus.BAD_REQUEST);
		}
		Session savedSession = session.get();
		savedSession.setStatus(SessionStatus.COMPLETED);
		sessionRepo.save(savedSession);
		return new ResponseEntity<Object>("Session Completed", HttpStatus.OK);	
	}

}
