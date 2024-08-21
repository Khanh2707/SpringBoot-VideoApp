package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.Video;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    Video toVideo(VideoCreationRequest request);

    VideoResponse toVideoResponse(Video video);
}
