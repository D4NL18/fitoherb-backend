package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.UserRecordDto;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<Optional<UserModel>> getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/users/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email, @RequestBody @Valid UserRecordDto userRecordDto) {
        return userService.updateUser(email, userRecordDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteAllUsers() {
        return userService.deleteAllUsers();
    }
}

