package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CommentVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentVideoUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentVideoUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.CommentVideoResponse;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentVideoResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.CommentVideoMapper;
import com.phuckhanh.VideoApp.mapper.HistoryNotificationCommentVideoMapper;
import com.phuckhanh.VideoApp.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentVideoService {
    CommentVideoMapper commentVideoMapper;
    ChannelRepository channelRepository;
    VideoRepository videoRepository;
    CommentVideoRepository commentVideoRepository;
    HistoryNotificationCommentVideoRepository historyNotificationCommentVideoRepository;
    CheckHistoryNotificationCommentVideoRepository checkHistoryNotificationCommentVideoRepository;
    HistoryNotificationCommentVideoMapper historyNotificationCommentVideoMapper;

    public long countHistoryNotificationCommentVideoFromTimeToTime(Integer idChannel) {
        CheckHistoryNotificationCommentVideo checkHistoryNotificationCommentVideo = checkHistoryNotificationCommentVideoRepository.findByChannel_IdChannel(idChannel).orElse(null);

        if (checkHistoryNotificationCommentVideo == null) {
            Channel channel = channelRepository.findById(idChannel)
                    .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

            checkHistoryNotificationCommentVideo = new CheckHistoryNotificationCommentVideo();
            checkHistoryNotificationCommentVideo.setDateTimeCheck(LocalDateTime.now());
            checkHistoryNotificationCommentVideo.setChannel(channel);

            checkHistoryNotificationCommentVideoRepository.save(checkHistoryNotificationCommentVideo);

            Pageable pageable = PageRequest.of(0, 1);

            return historyNotificationCommentVideoRepository.findAllByChannel_IdChannelOrderByCommentVideoDesc(idChannel, pageable).getTotalElements();
        } else {
            LocalDateTime dateTimeCheck = checkHistoryNotificationCommentVideo.getDateTimeCheck();
            LocalDateTime now = LocalDateTime.now();

            List<HistoryNotificationCommentVideo> historyNotificationCommentVideos = historyNotificationCommentVideoRepository.findAllByChannelIdAndTimeBetween(
                    idChannel, dateTimeCheck, now
            );

            return historyNotificationCommentVideos.size();
        }
    }

    public long countCommentVideosByVideo(Integer idVideo) {
        return commentVideoRepository.countCommentVideosByVideo(idVideo);
    }

    public Page<HistoryNotificationCommentVideoResponse> getAllNotificationCommentVideo(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyNotificationCommentVideoRepository.findAllByChannel_IdChannelOrderByCommentVideoDesc(idChannel, pageable)
                .map(historyNotificationCommentVideoMapper::toHistoryNotificationCommentVideoResponse);
    }

    public Page<CommentVideoResponse> getAllComment(Integer idVideo, String propertySort, String optionSort, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
        Pageable pageable = PageRequest.of(page, size, sort);

        return commentVideoRepository.findAllByVideo_IdVideo(idVideo, pageable)
                .map(commentVideoMapper::toCommentVideoResponse);
    }

    public CommentVideoResponse createCommentVideo(CommentVideoCreationRequest request) {
        CommentVideo commentVideo = commentVideoMapper.toCommentVideo(request);

        commentVideo.setDateTimeComment(LocalDateTime.now());

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        Video video = videoRepository.findById(request.getIdVideo()).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        commentVideo.setChannel(channel);
        commentVideo.setVideo(video);

        commentVideoRepository.save(commentVideo);

        HistoryNotificationCommentVideo historyNotificationCommentVideo = new HistoryNotificationCommentVideo();
        historyNotificationCommentVideo.setIdHistoryNotificationCommentVideoKey(new HistoryNotificationCommentVideoKey(commentVideo.getIdCommentVideo(), video.getChannel().getIdChannel()));
        historyNotificationCommentVideo.setCommentVideo(commentVideo);
        historyNotificationCommentVideo.setChannel(video.getChannel());
        historyNotificationCommentVideo.setIsCheck(false);
        historyNotificationCommentVideoRepository.save(historyNotificationCommentVideo);

        return commentVideoMapper.toCommentVideoResponse(commentVideo);
    }

    public void updateCheckHistoryNotificationCommentVideo(Integer idChannel) {
        Optional<CheckHistoryNotificationCommentVideo> optionalCheckHistory = checkHistoryNotificationCommentVideoRepository.findByChannel_IdChannel(idChannel);

        CheckHistoryNotificationCommentVideo checkHistoryNotificationCommentVideo;

        if (optionalCheckHistory.isPresent()) {
            checkHistoryNotificationCommentVideo = optionalCheckHistory.get();
        } else {
            checkHistoryNotificationCommentVideo = new CheckHistoryNotificationCommentVideo();
            Channel channel = channelRepository.findById(idChannel).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
            checkHistoryNotificationCommentVideo.setChannel(channel);
        }

        checkHistoryNotificationCommentVideo.setDateTimeCheck(LocalDateTime.now());

        checkHistoryNotificationCommentVideoRepository.save(checkHistoryNotificationCommentVideo);
    }

    public HistoryNotificationCommentVideoResponse updateIsCheckHistoryNotificationCommentVideo(Integer idChannel, Integer idNotificationCommentVideo, HistoryNotificationCommentVideoUpdateRequest request) {
        HistoryNotificationCommentVideoKey historyNotificationCommentVideoKey = new HistoryNotificationCommentVideoKey(idNotificationCommentVideo, idChannel);
        HistoryNotificationCommentVideo historyNotificationCommentVideo = historyNotificationCommentVideoRepository.findByIdHistoryNotificationCommentVideoKey(historyNotificationCommentVideoKey).orElseThrow(() -> new AppException(ErrorCode.HISTORY_NOTIFICATION_COMMENT_VIDEO_NOT_FOUND));

        historyNotificationCommentVideoMapper.updateHistoryNotificationCommentVideoIsCheck(historyNotificationCommentVideo, request);

        return historyNotificationCommentVideoMapper.toHistoryNotificationCommentVideoResponse(historyNotificationCommentVideoRepository.save(historyNotificationCommentVideo));
    }

    public CommentVideoResponse updateCommentVideoContent(Integer idCommentVideo, CommentVideoUpdateContentRequest request) {
        CommentVideo commentVideo = commentVideoRepository.findById(idCommentVideo).orElseThrow(() -> new AppException(ErrorCode.COMMENT_VIDEO_NOT_FOUND));

        commentVideoMapper.updateCommentVideoContent(commentVideo, request);

        commentVideo.setDateTimeComment(LocalDateTime.now());

        return commentVideoMapper.toCommentVideoResponse(commentVideoRepository.save(commentVideo));
    }

    public void deleteCommentVideo(Integer idCommentVideo) {
        CommentVideo commentVideo = commentVideoRepository.findById(idCommentVideo).orElseThrow(() -> new AppException(ErrorCode.COMMENT_VIDEO_NOT_FOUND));

        commentVideoRepository.delete(commentVideo);
    }
}
