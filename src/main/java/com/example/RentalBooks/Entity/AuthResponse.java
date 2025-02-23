package com.example.RentalBooks.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
  private final String message = "Success";
//  private String accessToken;

  public AuthResponse() {
  }
}