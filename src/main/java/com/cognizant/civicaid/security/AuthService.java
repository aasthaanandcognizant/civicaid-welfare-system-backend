package com.cognizant.civicaid.security;

import com.cognizant.civicaid.dto.LoginRequestDto;
import com.cognizant.civicaid.dto.SignUpRequestDto;
import com.cognizant.civicaid.dto.SignUpResponseDto;
import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //login service

//    public void Login(LoginRequestDto loginRequestDto){
//
//    }

    public SignUpResponseDto SignUp(SignUpRequestDto signUpRequestDto){

        User user=userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);

        if(user != null){
            throw new IllegalArgumentException("User already exists");
        }

        User newUser=modelMapper.map(signUpRequestDto,User.class);

        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        userRepository.save(newUser);

        return modelMapper.map(newUser,SignUpResponseDto.class);
    }
}
