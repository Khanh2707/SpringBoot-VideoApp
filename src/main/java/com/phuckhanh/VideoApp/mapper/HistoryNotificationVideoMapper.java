package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.HistoryNotificationVideoUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationVideoResponse;
import com.phuckhanh.VideoApp.entity.HistoryNotificationVideo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistoryNotificationVideoMapper {
    @Mapping(target = "idNotificationVideo", source = "notificationVideo.idNotificationVideo")
    @Mapping(target = "video", source = "notificationVideo.video")
    HistoryNotificationVideoResponse toHistoryNotificationVideoResponse(HistoryNotificationVideo historyNotificationVideo);

    void updateHistoryNotificationVideoIsCheck(@MappingTarget HistoryNotificationVideo historyNotificationVideo, HistoryNotificationVideoUpdateRequest request);
}
