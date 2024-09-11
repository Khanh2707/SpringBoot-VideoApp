package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    Video toVideo(VideoCreationRequest request);

    void updateVideo(@MappingTarget Video video, VideoUpdateRequest request);

    VideoResponse toVideoResponse(Video video);
}
