package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.TypeReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.TypeReportVideoResponse;
import com.phuckhanh.VideoApp.entity.TypeReportVideo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeReportVideoMapper {
    TypeReportVideo toTypeReportVideo(TypeReportVideoCreationRequest request);

    TypeReportVideoResponse toTypeReportVideoResponse(TypeReportVideo typeReportVideo);
}
