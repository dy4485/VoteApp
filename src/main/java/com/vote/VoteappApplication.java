package com.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vote.entity.Candidate;
import com.vote.repository.CandidateRepository;

@SpringBootApplication
public class VoteappApplication implements CommandLineRunner{
	

    @Autowired
    private CandidateRepository candidateRepo;

	public static void main(String[] args) {
		SpringApplication.run(VoteappApplication.class, args);
	}
	@Override
    public void run(String... args) {
        if (candidateRepo.count() == 0) {
        	candidateRepo.save(new Candidate("Candidate 1", 0));
        	candidateRepo.save(new Candidate("Candidate 2", 0));
        	candidateRepo.save(new Candidate("Candidate 3", 0));
        	candidateRepo.save(new Candidate("Candidate 4", 0));
        }
    }

}
