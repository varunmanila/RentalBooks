package com.example.RentalBooks.Service;

import com.example.RentalBooks.Entity.*;
import com.example.RentalBooks.Repocitory.UsersRepocitory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsersService implements UserDetailsService {
    private final UsersRepocitory userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsersService(UsersRepocitory userRepository, PasswordEncoder passwordEncoder,@Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }



    public AuthResponse registerUser(RegisterRequest request) {
        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setFirst_name(request.getFirstName());
        user.setLast_name(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if(Objects.nonNull(request.getRole())){
            user.setRole(Role.valueOf(request.getRole()));
        }else {
            user.setRole(Role.USER);
        }
        userRepository.save(user);
         return AuthResponse.builder().build();
    }

    public AuthResponse loginUser(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return AuthResponse.builder().build();
    }
}
