package com.vote.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.vote.entity.Candidate;
import com.vote.repository.CandidateRepository;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private CandidateRepository candidateRepo;

    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin_login";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("admin", "true");
            return "redirect:/admin/dashboard";
        }
        return "wrong_admin";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/admin/login";

        List<Candidate> candidates = candidateRepo.findAll();
        model.addAttribute("candidates", candidates);
        return "admin_dash";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
