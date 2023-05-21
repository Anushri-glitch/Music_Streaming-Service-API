package com.Shrishti.Music_StreamingServiceAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table
@NoArgsConstructor
public class AuthTokenUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;

    private String token;
    private LocalDate tokenCreationDate;

    @OneToOne
    @JoinColumn(nullable = false, name = "fk_user_Id")
    private User user;

    public AuthTokenUser(User user) {
        this.user = user;
        this.tokenCreationDate = LocalDate.now();
        this.token = UUID.randomUUID().toString();
    }
}
