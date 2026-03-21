package com.cognizant.civicaid.security;

import com.cognizant.civicaid.dto.LoginRequestDto;
import com.cognizant.civicaid.dto.LoginResponseDto;
import com.cognizant.civicaid.dto.SignUpRequestDto;
import com.cognizant.civicaid.dto.SignUpResponseDto;
import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.repository.UserRepository;
import com.cognizant.civicaid.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUtil authUtil;

    //login service

    public LoginResponseDto Login(LoginRequestDto loginRequestDto){

        //this authenticationManager will delegate(calls) to DaoAuthenticationProvider(calls your UserDetailsService.loadUserByUsername(...))
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );

        // getPrincipal->having all the details of authenticate user
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();

//        assert userDetails != null;
        if(userDetails==null) return null;

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
