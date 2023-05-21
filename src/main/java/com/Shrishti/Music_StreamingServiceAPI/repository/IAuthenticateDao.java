package com.Shrishti.Music_StreamingServiceAPI.repository;

import com.Shrishti.Music_StreamingServiceAPI.model.AuthTokenUser;
import com.Shrishti.Music_StreamingServiceAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticateDao extends JpaRepository<AuthTokenUser,Integer> {
    AuthTokenUser findByUser(User user);
}
