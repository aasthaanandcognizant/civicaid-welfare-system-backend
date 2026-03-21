package com.cognizant.civicaid.security;

import com.cognizant.civicaid.dto.request.LoginRequestDto;
import com.cognizant.civicaid.dto.response.LoginResponseDto;
import com.cognizant.civicaid.dto.request.SignUpRequestDto;
import com.cognizant.civicaid.dto.response.SignUpResponseDto;
import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.repository.UserRepository;
import com.cognizant.civicaid.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    //login service

    public LoginResponseDto Login(LoginRequestDto loginRequestDto){

        //this authenticationManager will delegate(calls) to DaoAuthenticationProvider(calls your UserDetailsService.loadUserByUsername(...))
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );

        // getPrincipal->having all the details of authenticate user
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();

        if(userDetails==null) return null;

        //auditlog

        String token=authUtil.generateJwtToken(userDetails);

        return new LoginResponseDto(token);
    }

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
