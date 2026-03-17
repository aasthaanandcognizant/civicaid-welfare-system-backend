package com.cognizant.civicaid.controller;

import com.cognizant.civicaid.dto.LoginRequestDto;
import com.cognizant.civicaid.dto.SignUpRequestDto;
import com.cognizant.civicaid.dto.SignUpResponseDto;
import com.cognizant.civicaid.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //login controller

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody LoginRequestDto loginRequestDto){

//        return ResponseEntity.ok("Logged i Successfully.");


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("You Logged in successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto>signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.SignUp(signUpRequestDto));
    }

}
