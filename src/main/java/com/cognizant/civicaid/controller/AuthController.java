package com.cognizant.civicaid.controller;

import com.cognizant.civicaid.dto.request.LoginRequestDto;
import com.cognizant.civicaid.dto.response.LoginResponseDto;
import com.cognizant.civicaid.dto.request.SignUpRequestDto;
import com.cognizant.civicaid.dto.response.SignUpResponseDto;
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
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.Login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto>signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.SignUp(signUpRequestDto));
    }

}
