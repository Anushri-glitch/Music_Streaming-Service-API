package com.Shrishti.Music_StreamingServiceAPI.repository;

import com.Shrishti.Music_StreamingServiceAPI.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminDao extends JpaRepository<Admin, Integer> {
    Admin findFirstByAdminEmail(String email);

    Admin findByAdminEmail(String signInMail);
}
