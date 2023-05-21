package com.Shrishti.Music_StreamingServiceAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer adminId;
    private String adminUserName;

    @Pattern(regexp = "^[a-z0-9]{3,}@[admin]{3,5}[.]{1}[com]{1,3}$")
    private String adminEmail;
    private String adminPassword;
    private String adminPhone;

    public Admin(String adminUserName, String adminEmail, String adminPassword, String adminPhone) {
        this.adminUserName = adminUserName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminPhone = adminPhone;
    }
}
