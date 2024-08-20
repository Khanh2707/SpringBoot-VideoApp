package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyEmailResponse {
    String idVerifyEmail;
    String email;
    String code;
    LocalDateTime dateTimeExpiry;
}
