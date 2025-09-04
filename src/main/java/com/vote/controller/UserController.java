package com.vote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vote.entity.Candidate;
import com.vote.entity.User;
import com.vote.repository.CandidateRepository;
import com.vote.repository.UserRepository;


import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private CandidateRepository candidateRepo;
	
	    @GetMapping("/register")
	    public String registerPage(Model model) {
	        model.addAttribute("user", new User());
	        return "register";
	    }

	    @PostMapping("/register")
	    public String register(@ModelAttribute User user,Model model) {
	    	String username = user.getUsername();

	        if (userRepo.existsByUsername(username)) {
	            model.addAttribute("error", "Username already exists!");
	            return "register"; 
	        }
	        userRepo.save(user);
	        return "redirect:/login";
	    }

	    @GetMapping("/login")
	    public String loginPage() {
	        return "login";
	    }

	    @PostMapping("/login")
	    public String login(@RequestParam String username,@RequestParam String password,HttpSession session,Model model) {
	        User user = userRepo.findByUsername(username);
	        if (user != null && user.getPassword().equals(password)) {
	            session.setAttribute("user", user);
	            return "redirect:/voteapp";
	        } else {
	        	 model.addAttribute("error", "You have entered wrong userid and password!!");
	            return "login";
	        }
	    }

	    @GetMapping("/voteapp")
	    public String votePage(HttpSession session, Model model) {
	        User user = (User) session.getAttribute("user");
	        if (user == null) return "redirect:/login";

	        if (user.isHasVoted()) return "alreadyVote";

	        List<Candidate> candidates = candidateRepo.findAll();
	        model.addAttribute("candidates", candidates);
	        return "voteapp";
	    }

	    @PostMapping("/voteapp")
	    public String vote(@RequestParam int candidateId, HttpSession session) {
	        User user = (User) session.getAttribute("user");
	        if (user == null || user.isHasVoted()) return "alreadyvote";

	        Candidate candidate = candidateRepo.findById(candidateId).orElseThrow();
	        if (candidate != null) {
	            candidate.setVoteCount(candidate.getVoteCount() + 1);
	            candidateRepo.save(candidate);
	            user.setHasVoted(true);
	            userRepo.save(user);
	        }

	        return "success";
	    }

	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login";
	    }
	}



