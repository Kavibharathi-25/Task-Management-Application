package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String rawPassword = body.get("password");

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }

        userService.createUser(
                User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(rawPassword)) // ✅ encode ONCE
                        .build()
        );

        return new ResponseEntity<>("Successfully Registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")


    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "User not Registered"),
                    HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(Map.of("error", "Invalid User"),
                    HttpStatus.UNAUTHORIZED);
        }


        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }


}



