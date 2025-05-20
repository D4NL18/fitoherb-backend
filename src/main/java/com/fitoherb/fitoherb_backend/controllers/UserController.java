package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.UserEditDto;
import com.fitoherb.fitoherb_backend.dtos.UserRecordDto;
import com.fitoherb.fitoherb_backend.models.UserModel;
import com.fitoherb.fitoherb_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/id={id}")
    public ResponseEntity<UserModel> getUserByEmail(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") UUID id,
                                             @RequestParam("user_name") String user_name,
                                             @RequestParam("email") String email,
                                             @RequestParam("password") String password,
                                             @RequestParam("isAdmin") boolean isAdmin
    ) {

        UserEditDto userEditDTO = new UserEditDto(user_name, email, password, isAdmin);
        return userService.updateUser(id, userEditDTO);
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

