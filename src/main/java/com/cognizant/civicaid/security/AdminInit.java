package com.cognizant.civicaid.security;

import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        final String adminEmail="admin@gmail.com";

        boolean exist=userRepository.existsByEmail(adminEmail);

        if(!exist){
            User admin=new User();

            admin.setEmail("governmentauditor@gmail.com");
            admin.setPassword(passwordEncoder.encode("governmentauditor12"));
            admin.setRole(User.Role.GOVERNMENT_AUDITOR);
            userRepository.save(admin);

            System.out.println("Admin Register Success");
        }else{
            log.info(" ADMIN already exists with email: {}", adminEmail);
        }
    }
}
