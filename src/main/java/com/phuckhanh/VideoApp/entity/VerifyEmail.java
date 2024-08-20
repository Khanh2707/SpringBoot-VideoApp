package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class VerifyEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idVerifyEmail;
    String email;
    String code;
    LocalDateTime dateTimeExpiry;
}
