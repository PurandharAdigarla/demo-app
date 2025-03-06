package com.recykal.register.controller;

import com.recykal.register.entity.User;
import com.recykal.register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully. A confirmation email has been sent.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/viewAllUsers")
    public ResponseEntity<Iterable<User>> viewAllUsers() {
        return new ResponseEntity<Iterable<User>>(userService.viewAllUsers(), HttpStatus.OK);
    }
}
