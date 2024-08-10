package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.ChannelCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.entity.Channel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    Channel toChannel(ChannelCreationRequest request);

    ChannelResponse toChannelResponse(Channel channel);
}
