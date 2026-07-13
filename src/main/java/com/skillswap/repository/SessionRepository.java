package com.skillswap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillswap.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
