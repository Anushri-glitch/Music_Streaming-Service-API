package com.Shrishti.Music_StreamingServiceAPI.repository;

import com.Shrishti.Music_StreamingServiceAPI.model.Admin;
import com.Shrishti.Music_StreamingServiceAPI.model.AuthTokenAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticateAdminDao extends JpaRepository<AuthTokenAdmin,Integer> {
    AuthTokenAdmin findByAdmin(Admin admin);
}
