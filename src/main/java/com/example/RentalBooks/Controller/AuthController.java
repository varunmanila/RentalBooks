package com.example.RentalBooks.Controller;

import com.example.RentalBooks.Entity.LoginRequest;
import com.example.RentalBooks.Entity.RegisterRequest;
import com.example.RentalBooks.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsersService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        userService.loginUser(loginRequest);
        return ResponseEntity.ok("Login Success");
    }

}