package com.Shrishti.Music_StreamingServiceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInput {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String contact;
}