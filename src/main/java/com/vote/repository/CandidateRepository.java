package com.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote.entity.Candidate;
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer>{

	
}
