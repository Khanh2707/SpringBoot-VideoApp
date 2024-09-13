package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.BanChannelCreationRequest;
import com.phuckhanh.VideoApp.dto.response.BanChannelResponse;
import com.phuckhanh.VideoApp.entity.BanChannel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BanChannelMapper {
    BanChannel toBanChannel(BanChannelCreationRequest request);

    BanChannelResponse toBanChannelResponse(BanChannel banChannel);
}
