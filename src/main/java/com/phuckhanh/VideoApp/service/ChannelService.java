package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.ChannelSubChannelRequest;
import com.phuckhanh.VideoApp.dto.request.ChannelUpdateAvatarRequest;
import com.phuckhanh.VideoApp.dto.request.ChannelUpdateInfoRawRequest;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.ChannelSubChannel;
import com.phuckhanh.VideoApp.entity.ChannelSubChannelKey;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.ChannelMapper;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.ChannelSubChannelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChannelService {
    ChannelSubChannelRepository channelSubChannelRepository;
    ChannelRepository channelRepository;
    ChannelMapper channelMapper;
    CloudinaryService cloudinaryService;

    public long countUser1SubForUser2(Integer idChannel2) {
        return channelSubChannelRepository.countUser1SubForUser2(idChannel2);
    }

    public boolean isUser1SubscribingUser2(Integer idChannel1, Integer idChannel2) {
        return channelSubChannelRepository.isUser1SubscribingUser2(idChannel1, idChannel2);
    }

    public List<ChannelResponse> getAllSubChannel(Integer idChannel2) {
        return channelSubChannelRepository.findByChannel2_IdChannel(idChannel2).stream()
                .map(channelSubChannel -> channelMapper.toChannelResponse(channelSubChannel.getChannel1()))
                .toList();
    }

    public void createChannelSubChannel(ChannelSubChannelRequest request) {
        ChannelSubChannel channelSubChannel = new ChannelSubChannel();

        channelSubChannel.setChannel1(channelRepository.findById(request.getIdChannel1()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND)));
        channelSubChannel.setChannel2(channelRepository.findById(request.getIdChannel2()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND)));
        channelSubChannel.setIdChannelSubChannelKey(new ChannelSubChannelKey(request.getIdChannel1(), request.getIdChannel2()));

        channelSubChannelRepository.save(channelSubChannel);
    }

    public ChannelResponse updateChannelAvatar(Integer idChannel, ChannelUpdateAvatarRequest request) throws IOException {
        Channel channel = channelRepository.findById(idChannel).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

        cloudinaryService.destroyFile("avatar channel", channel.getAvatar(), "image");

        if (!request.getDelete().equals(true)) {
            String urlAvatar = (String) cloudinaryService
                    .uploadFile(request.getFileAvatar(), "avatar channel", "")
                    .get("secure_url");

            channel.setAvatar(urlAvatar);
        } else {
            channel.setAvatar(null);
        }

        return channelMapper.toChannelResponse(channelRepository.save(channel));
    }

    public ChannelResponse updateChannelInfoRaw(Integer idChannel, ChannelUpdateInfoRawRequest request) {
        Channel channel = channelRepository.findById(idChannel).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

        if (channelRepository.existsByNameUnique(request.getNameUnique()))
            throw new AppException(ErrorCode.CHANNEL_NAME_UNIQUE_EXISTED);

        channelMapper.updateChannelInfoRaw(channel, request);

        return channelMapper.toChannelResponse(channelRepository.save(channel));
    }

    public void deleteChannelSubChannel(Integer idChannel1, Integer idChannel2) {
        ChannelSubChannel channelSubChannel = channelSubChannelRepository.findById(new ChannelSubChannelKey(idChannel1, idChannel2))
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_SUB_CHANNEL_NOT_FOUND));

        channelSubChannelRepository.delete(channelSubChannel);
    }
}
