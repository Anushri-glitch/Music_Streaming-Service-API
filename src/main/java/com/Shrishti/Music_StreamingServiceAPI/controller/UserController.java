package com.Shrishti.Music_StreamingServiceAPI.controller;

import com.Shrishti.Music_StreamingServiceAPI.dto.SignInInput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignInOutput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignUpInput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignUpOutput;
import com.Shrishti.Music_StreamingServiceAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    //User SignUp
    @PostMapping(value = "/signUp")
    public SignUpOutput SignUp(@RequestBody SignUpInput signUpDto){

        return userService.SignUp(signUpDto);
    }

    //User SignIn
    @PostMapping(value = "/signIn")
    public SignInOutput SignIn(@RequestBody SignInInput signInDto){
        return userService.SignIn(signInDto);
    }

    //Admin SignUp
    @PostMapping(value = "/signUpAdmin")
    public SignUpOutput SignUpAdmin(@RequestBody SignUpInput signUpDto){

        return userService.SignUpAdmin(signUpDto);
    }

    //Admin SignIn
    @PostMapping(value = "/signInAdmin")
    public SignInOutput SignInAdmin(@RequestBody SignInInput signInDto){
        return userService.SignInAdmin(signInDto);
    }
}
