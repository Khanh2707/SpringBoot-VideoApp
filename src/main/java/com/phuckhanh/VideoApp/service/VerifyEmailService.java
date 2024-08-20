package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.VerifyEmailGenerateRequest;
import com.phuckhanh.VideoApp.dto.response.VerifyEmailResponse;
import com.phuckhanh.VideoApp.entity.VerifyEmail;
import com.phuckhanh.VideoApp.mapper.VerifyEmailMapper;
import com.phuckhanh.VideoApp.repository.VerifyEmailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VerifyEmailService {

    VerifyEmailRepository verifyEmailRepository;

    VerifyEmailMapper verifyEmailMapper;

    EmailService emailService;

    public VerifyEmailResponse getVerifyEmailNewest(String email) {
        return verifyEmailMapper.toVerifyEmailResponse(verifyEmailRepository.findTop1ByEmailOrderByDateTimeExpiryDesc(email));
    }

    public VerifyEmailResponse verifyEmailGenerate(VerifyEmailGenerateRequest request) {
        VerifyEmail verifyEmail = verifyEmailMapper.toVerifyEmail(request);

        String randomCode = UUID.randomUUID().toString();
        verifyEmail.setCode(randomCode);

        verifyEmail.setDateTimeExpiry(LocalDateTime.now().withNano(0).plusMinutes(30));

        emailService.sendMail(request.getEmail(), "Xác nhận chủ nhân của mail", "Mã xác nhận của bạn là:", randomCode);

        return verifyEmailMapper.toVerifyEmailResponse(verifyEmailRepository.save(verifyEmail));
    }
}
