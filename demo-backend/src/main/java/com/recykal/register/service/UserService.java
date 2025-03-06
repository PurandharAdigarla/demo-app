package com.recykal.register.service;

import com.recykal.register.entity.User;
import com.recykal.register.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        Optional<User> existingUser = userRepo.findById(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        emailService.sendEmail(user.getEmail(), "Registration Successful",
                "Congratulations " + user.getName() + "\nYour registration is successful.");
    }

    public List<User> viewAllUsers()
    {
        if(userRepo.findAll().isEmpty())
            System.out.println("Empty");
        return userRepo.findAll();
    }
}
