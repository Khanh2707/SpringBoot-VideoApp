package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.VerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, String> {
    VerifyEmail findTop1ByEmailOrderByDateTimeExpiryDesc(String email);
}
