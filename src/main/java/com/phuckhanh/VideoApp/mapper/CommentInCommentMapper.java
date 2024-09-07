package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.CommentInCommentCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentInCommentUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.CommentInCommentResponse;
import com.phuckhanh.VideoApp.entity.CommentInComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentInCommentMapper {
    CommentInComment toCommentInComment(CommentInCommentCreationRequest request);

    void updateCommentCommentContent(@MappingTarget CommentInComment commentInComment, CommentInCommentUpdateContentRequest request);

    CommentInCommentResponse toCommentInCommentResponse(CommentInComment commentInComment);
}
