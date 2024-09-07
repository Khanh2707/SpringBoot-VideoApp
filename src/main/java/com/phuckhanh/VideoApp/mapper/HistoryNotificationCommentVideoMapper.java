package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentVideoResponse;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryNotificationCommentVideoMapper {
    @Mapping(target = "commentVideo", source = "commentVideo")
    HistoryNotificationCommentVideoResponse toHistoryNotificationCommentVideoResponse(HistoryNotificationCommentVideo historyNotificationCommentVideo);
}
