package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.LoginRequestDto;
import com.fitoherb.fitoherb_backend.dtos.RegisterRequestDto;
import com.fitoherb.fitoherb_backend.dtos.ResponseDto;
import com.fitoherb.fitoherb_backend.infra.security.TokenService;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body) {
        UserModel user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getUser_name(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body) {
        Optional<UserModel> user = this.userRepository.findByEmail(body.email());

        if(user.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUser_name(body.user_name());
            this.userRepository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getUser_name(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
