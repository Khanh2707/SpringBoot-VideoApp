package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.VerifyEmailGenerateRequest;
import com.phuckhanh.VideoApp.dto.response.VerifyEmailResponse;
import com.phuckhanh.VideoApp.entity.VerifyEmail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerifyEmailMapper {
    VerifyEmail toVerifyEmail(VerifyEmailGenerateRequest request);

    VerifyEmailResponse toVerifyEmailResponse(VerifyEmail verifyEmail);
}
