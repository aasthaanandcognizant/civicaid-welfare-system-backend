package com.cognizant.civicaid.dto.response;

import com.cognizant.civicaid.entity.User.Role;
import lombok.Data;

@Data
public class SignUpResponseDto {

    private String name;
    private String email;
    private Long userId;
    private Role role;
    private String phone;

}
