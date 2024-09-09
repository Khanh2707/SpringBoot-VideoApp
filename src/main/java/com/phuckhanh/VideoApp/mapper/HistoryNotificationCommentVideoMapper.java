package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentVideoUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentVideoResponse;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistoryNotificationCommentVideoMapper {
    @Mapping(target = "commentVideo", source = "commentVideo")
    HistoryNotificationCommentVideoResponse toHistoryNotificationCommentVideoResponse(HistoryNotificationCommentVideo historyNotificationCommentVideo);

    void updateHistoryNotificationCommentVideoIsCheck(@MappingTarget HistoryNotificationCommentVideo historyNotificationCommentVideo, HistoryNotificationCommentVideoUpdateRequest request);
}
