package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.model.Polls;

@Repository
public interface PollRepository extends JpaRepository<Polls, Long>{
    
}
