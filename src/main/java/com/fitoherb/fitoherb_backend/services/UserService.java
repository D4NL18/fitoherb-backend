package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.UserRecordDto;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<Optional<UserModel>> getUserByEmail(String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

    public ResponseEntity<Object> updateUser(String email, UserRecordDto userRecordDto) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        Optional<UserModel> newUserInfo = userRepository.findByEmail(userRecordDto.email());
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }else if(!newUserInfo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Used");
        }
        var userModel = user.get();
        BeanUtils.copyProperties(userRecordDto, userModel);
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
