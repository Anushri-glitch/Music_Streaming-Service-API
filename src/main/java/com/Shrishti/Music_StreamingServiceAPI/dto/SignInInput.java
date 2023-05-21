package com.Shrishti.Music_StreamingServiceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInInput {

    private String signInMail;
    private String signInPassword;
}
