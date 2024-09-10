package com.project.test_student.controller;

import com.project.test_student.domain.User;
import com.project.test_student.dto.UserLogin;
import com.project.test_student.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user){

        if(userService.findByUsername(user.getUsername()) != null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserLogin login){

        String token = userService.loginUserValidation(login);

        return ResponseEntity.ok(token);

    }
}
