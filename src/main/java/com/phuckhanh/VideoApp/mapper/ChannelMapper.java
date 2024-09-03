package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.ChannelCreationRequest;
import com.phuckhanh.VideoApp.dto.request.ChannelUpdateAvatarRequest;
import com.phuckhanh.VideoApp.dto.request.ChannelUpdateInfoRawRequest;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    Channel toChannel(ChannelCreationRequest request);

    void updateChannelAvatar(@MappingTarget Channel channel, ChannelUpdateAvatarRequest request);

    void updateChannelInfoRaw(@MappingTarget Channel channel, ChannelUpdateInfoRawRequest request);

    ChannelResponse toChannelResponse(Channel channel);
}
