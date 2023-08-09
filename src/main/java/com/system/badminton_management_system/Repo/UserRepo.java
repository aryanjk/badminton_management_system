package com.system.badminton_management_system.Repo;

import com.system.badminton_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value="select * from users where email=?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "UPDATE users SET password =?1 WHERE email = ?2", nativeQuery = true)
    void updatePassword(String password, String email);
}
