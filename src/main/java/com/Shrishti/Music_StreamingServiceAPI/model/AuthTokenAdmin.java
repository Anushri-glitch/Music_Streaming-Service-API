package com.Shrishti.Music_StreamingServiceAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class AuthTokenAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminTokenId;

    private String adminToken;
    private LocalDate adminTokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "fk_user_Id")
    private Admin admin;

    public AuthTokenAdmin(Admin admin) {
        this.admin = admin;
        this.adminTokenCreationDate = LocalDate.now();
        this.adminToken = UUID.randomUUID().toString();
    }
}
