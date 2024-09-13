package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.BanChannelCreationRequest;
import com.phuckhanh.VideoApp.dto.response.BanChannelResponse;
import com.phuckhanh.VideoApp.entity.BanChannel;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.BanChannelMapper;
import com.phuckhanh.VideoApp.repository.BanChannelRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BanChannelService {
    ChannelRepository channelRepository;
    BanChannelMapper banChannelMapper;
    BanChannelRepository banChannelRepository;

    public BanChannelResponse getChannelIsBan(Integer idChannel) {
        BanChannel banChannel = banChannelRepository.findByChannel_IdChannel(idChannel).orElseThrow(() -> new AppException(ErrorCode.BAN_CHANNEL_NOT_FOUND));

        return banChannelMapper.toBanChannelResponse(banChannel);
    }

    public BanChannelResponse createBanChannel(BanChannelCreationRequest request) {
        BanChannel banChannel = banChannelMapper.toBanChannel(request);

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

        banChannel.setReason("Bạn đã bị khóa việc đăng video");
        banChannel.setDateTimeBan(LocalDateTime.now());
        banChannel.setChannel(channel);

        return banChannelMapper.toBanChannelResponse(banChannelRepository.save(banChannel));
    }
}
