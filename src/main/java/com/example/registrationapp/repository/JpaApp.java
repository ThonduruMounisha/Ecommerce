package com.example.registrationapp.repository;

import com.example.registrationapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaApp extends JpaRepository <Users ,Long>{
    Users findByEmail(String emailid);

    Users findByEmailAndPassword(String emailid, String password);

    Users findByResetToken(String resetToken); // If not already added
}
