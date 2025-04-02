package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.UserEditDto;
import com.fitoherb.fitoherb_backend.dtos.UserRecordDto;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<Optional<UserModel>> getUserByEmail(String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

    public ResponseEntity<UserModel> getUserById(UUID id) {
        return ResponseEntity.ok(userRepository.findById(id).orElse(null));
    }

    public ResponseEntity<Object> updateUser(UUID id, UserEditDto userRecordDto) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        var userModel = user.get();

        if(userRecordDto.password().isBlank()) {
            UserRecordDto userRecordDto1 = new UserRecordDto(userRecordDto.user_name(), userRecordDto.email(), userModel.getPassword());
            BeanUtils.copyProperties(userRecordDto1, userModel);
        }else {
            BeanUtils.copyProperties(userRecordDto, userModel);
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        }
        return ResponseEntity.ok(userRepository.save(userModel));
    }

    public ResponseEntity<Object> deleteUser(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok().body("Deleted");
    }

    public ResponseEntity<Object> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
