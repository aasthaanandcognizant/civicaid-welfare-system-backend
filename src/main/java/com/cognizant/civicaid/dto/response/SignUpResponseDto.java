package com.cognizant.civicaid.dto.response;

import lombok.Data;

@Data
public class SignUpResponseDto {

    private String name;
    private String email;
    private Long userId;
    private String role;
    private String phone;

}
