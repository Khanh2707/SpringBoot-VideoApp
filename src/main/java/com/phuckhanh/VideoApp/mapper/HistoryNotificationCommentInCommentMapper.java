package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentInCommentResponse;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryNotificationCommentInCommentMapper {
    @Mapping(target = "commentInComment", source = "commentInComment")
    HistoryNotificationCommentInCommentResponse toHistoryNotificationCommentInCommentResponse(HistoryNotificationCommentInComment historyNotificationCommentInComment);
}
