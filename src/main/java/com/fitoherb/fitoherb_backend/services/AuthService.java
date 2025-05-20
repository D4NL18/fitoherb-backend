package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.LoginRequestDto;
import com.fitoherb.fitoherb_backend.dtos.RegisterRequestDto;
import com.fitoherb.fitoherb_backend.dtos.ResponseDto;
import com.fitoherb.fitoherb_backend.infra.security.TokenService;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseEntity login(LoginRequestDto body) {
        UserModel user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getUser_name(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity register(RegisterRequestDto body) {
        Optional<UserModel> user = this.userRepository.findByEmail(body.email());

        if(user.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUser_name(body.user_name());
            newUser.setAdmin(body.isAdmin());
            this.userRepository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getUser_name(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
