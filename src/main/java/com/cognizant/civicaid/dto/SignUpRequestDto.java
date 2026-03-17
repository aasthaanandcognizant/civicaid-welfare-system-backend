package com.cognizant.civicaid.dto;

import com.cognizant.civicaid.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    private String name;
    private String password;
    private String email;
    private Role role;
    private String phone;
    private String status;

}
