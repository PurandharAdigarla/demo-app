package com.recykal.register.controller;

import com.recykal.register.entity.User;
import com.recykal.register.repository.IUserRepository;
import com.recykal.register.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/authentication")
public class AuthController {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User login) {
        Optional<User> userOptional = userRepo.findById(login.getEmail());

        if (userOptional.isPresent() && passwordEncoder.matches(login.getPassword(), userOptional.get().getPassword())) {
            User user= userOptional.get();
            emailService.sendEmail(user.getEmail(), "Login Alert",
                    "Hello " +user.getName() + "," + "\n\nYou have been successfully logged into your account on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}

