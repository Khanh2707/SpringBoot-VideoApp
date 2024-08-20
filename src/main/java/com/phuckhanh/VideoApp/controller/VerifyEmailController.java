package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.VerifyEmailGenerateRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.VerifyEmailResponse;
import com.phuckhanh.VideoApp.service.VerifyEmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify_email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VerifyEmailController {

    VerifyEmailService verifyEmailService;

    @PostMapping("")
    ApiResponse<VerifyEmailResponse> generateVerifyEmail(@RequestBody VerifyEmailGenerateRequest request) {
        return ApiResponse.<VerifyEmailResponse>builder()
                .result(verifyEmailService.verifyEmailGenerate(request))
                .build();
    }
}
