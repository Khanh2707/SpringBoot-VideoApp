package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentInCommentUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentInCommentResponse;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistoryNotificationCommentInCommentMapper {
    @Mapping(target = "commentInComment", source = "commentInComment")
    HistoryNotificationCommentInCommentResponse toHistoryNotificationCommentInCommentResponse(HistoryNotificationCommentInComment historyNotificationCommentInComment);

    void updateHistoryNotificationCommentInCommentIsCheck(@MappingTarget HistoryNotificationCommentInComment historyNotificationCommentInComment, HistoryNotificationCommentInCommentUpdateRequest request);
}
