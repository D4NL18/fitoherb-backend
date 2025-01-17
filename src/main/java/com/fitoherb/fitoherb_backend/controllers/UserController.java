package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.ProductRecordDto;
import com.fitoherb.fitoherb_backend.dtos.UserRecordDto;
import com.fitoherb.fitoherb_backend.models.ProductModel;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.repositories.ProductRepository;
import com.fitoherb.fitoherb_backend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<Optional<UserModel>> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userRepository.findByEmail(email));
    }

    @PutMapping("/users/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email, @RequestBody @Valid UserRecordDto userRecordDto) {
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

    @DeleteMapping("/users/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable("email") String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok().body("Deleted");
    }

    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}

