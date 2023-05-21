package com.Shrishti.Music_StreamingServiceAPI.repository;

import com.Shrishti.Music_StreamingServiceAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao  extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String userEmail);
}
