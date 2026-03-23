package com.cognizant.civicaid.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;

}
