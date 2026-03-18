package com.cognizant.civicaid.security;

import com.cognizant.civicaid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

         com.cognizant.civicaid.entity.User user=userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not Exist."));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());


        //user data from DB having hashedPassword
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(authority)
        );
    }
}
