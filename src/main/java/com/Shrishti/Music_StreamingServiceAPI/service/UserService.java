package com.Shrishti.Music_StreamingServiceAPI.service;

import com.Shrishti.Music_StreamingServiceAPI.dto.SignInInput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignInOutput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignUpInput;
import com.Shrishti.Music_StreamingServiceAPI.dto.SignUpOutput;
import com.Shrishti.Music_StreamingServiceAPI.model.Admin;
import com.Shrishti.Music_StreamingServiceAPI.model.AuthTokenAdmin;
import com.Shrishti.Music_StreamingServiceAPI.model.AuthTokenUser;
import com.Shrishti.Music_StreamingServiceAPI.model.User;
import com.Shrishti.Music_StreamingServiceAPI.repository.IAdminDao;
import com.Shrishti.Music_StreamingServiceAPI.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    IUserDao userDao;

    @Autowired
    IAdminDao adminDao;

    @Autowired
    AuthenticateService authenticateService;

    public SignUpOutput SignUp(SignUpInput signUpDto) {

        //Check If User Exist or not based on User Email
        User user = userDao.findFirstByUserEmail(signUpDto.getEmail());

        if(user != null){
            throw new IllegalStateException("User Already Registered!!");
        }

        //Encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //Save User
        String userName = (signUpDto.getFirstName() + signUpDto.getLastName());
        System.out.println(encryptedPassword);
        user = new User(userName, signUpDto.getEmail(), encryptedPassword , signUpDto.getContact());
        userDao.save(user);

        //token creation and saving
        AuthTokenUser token = new AuthTokenUser(user);
        authenticateService.saveToken(token);

        return new SignUpOutput("User Registered!!! as - " + user.getUserName(), "User Created Successfully!!!");
    }

    private String encryptPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(password.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }


    public SignInOutput SignIn(SignInInput signInDto) {

        //Check By Email
        User user = userDao.findFirstByUserEmail(signInDto.getSignInMail());
        if(user == null){
            throw new IllegalStateException("User Does Not Exist!!!.....SignUp Please");
        }

        //encrypt Password
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signInDto.getSignInPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        //match it with database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(user.getUserPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("User Invalid!!....Signup Again!!");
        }

        //figure out the token
        AuthTokenUser authToken = authenticateService.getToken(user);

        //setup output response
        return new SignInOutput("Authentication Successful!!!" + user.getUserName(), authToken.getToken());
    }

    public SignUpOutput SignUpAdmin(SignUpInput signUpDto) {
        //Check If User Exist or not based on User Email
        Admin admin = adminDao.findFirstByAdminEmail(signUpDto.getEmail());

        if(admin != null){
            throw new IllegalStateException("Admin Already Registered!!");
        }

        //Encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //Save User
        String userName = (signUpDto.getFirstName() + signUpDto.getLastName());
        System.out.println(encryptedPassword);
        admin = new Admin(userName, signUpDto.getEmail(), encryptedPassword , signUpDto.getContact());
        adminDao.save(admin);

        //token creation and saving
        AuthTokenAdmin token = new AuthTokenAdmin(admin);
        authenticateService.saveAdminToken(token);

        return new SignUpOutput("Admin Registered!!! as  - " + admin.getAdminUserName(), "Admin Created Successfully!!!");
    }

    public SignInOutput SignInAdmin(SignInInput signInDto) {
        //Check By Email
        Admin admin = adminDao.findByAdminEmail(signInDto.getSignInMail());
        if(admin == null){
            throw new IllegalStateException("Admin Does Not Exist!!!.....SignUp Please");
        }

        //encrypt Password
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signInDto.getSignInPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        //match it with database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(admin.getAdminPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("Admin Invalid!!....Signup Again!!");
        }

        //figure out the token
        AuthTokenAdmin authToken = authenticateService.getAdminToken(admin);

        //setup output response
        return new SignInOutput("Authentication Successful!!!" + admin.getAdminUserName(), authToken.getAdminToken());
    }
}

