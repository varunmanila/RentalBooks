package com.example.RentalBooks.Entity;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
