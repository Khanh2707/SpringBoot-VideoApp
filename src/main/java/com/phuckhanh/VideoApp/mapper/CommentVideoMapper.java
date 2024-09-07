package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.CommentVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentVideoUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.CommentVideoResponse;
import com.phuckhanh.VideoApp.entity.CommentVideo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentVideoMapper {
    CommentVideo toCommentVideo(CommentVideoCreationRequest request);

    void updateCommentVideoContent(@MappingTarget CommentVideo commentVideo, CommentVideoUpdateContentRequest request);

    CommentVideoResponse toCommentVideoResponse(CommentVideo commentVideo);
}
