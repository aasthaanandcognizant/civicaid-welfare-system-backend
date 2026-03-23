package com.cognizant.civicaid.security;

import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficerInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{

        createOfficer("welfareofficer@gmail.com", "welfareofficer", User.Role.WELFARE_OFFICER);
        createOfficer("complianceofficer@gmail.com", "complianceofficer", User.Role.COMPLIANCE_OFFICER);
        createOfficer("administrator@gmail.com", "administrator", User.Role.ADMINISTRATOR);
        createOfficer("governmentauditor@gmail.com", "governmentauditor",User.Role.GOVERNMENT_AUDITOR);
        createOfficer("programmanager@gmail.com", "programmanager",User.Role.PROGRAM_MANAGER);
    }

    private void createOfficer(String email, String rawPassword, User.Role role) {
        if (userRepository.existsByEmail(email)) {
            return;
        }

        User officer = new User();
        officer.setEmail(email);
        officer.setPassword(passwordEncoder.encode(rawPassword));
        officer.setRole(role);

        userRepository.save(officer);
        System.out.println("Created officer: " + email);
    }
}