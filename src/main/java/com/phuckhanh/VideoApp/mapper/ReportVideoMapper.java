package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.ReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ReportVideoResponse;
import com.phuckhanh.VideoApp.entity.ReportVideo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportVideoMapper {
    ReportVideo toReportVideo(ReportVideoCreationRequest request);

    ReportVideoResponse toReportVideoResponse(ReportVideo video);
}
