package com.cognizant.civicaid.dto.request;

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
    private String role;
    private String phone;
    private String status;

}
