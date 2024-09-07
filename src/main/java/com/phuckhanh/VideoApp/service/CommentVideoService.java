package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CommentVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentVideoUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.CommentVideoResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.CommentVideoMapper;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.CommentVideoRepository;
import com.phuckhanh.VideoApp.repository.HistoryNotificationCommentVideoRepository;
import com.phuckhanh.VideoApp.repository.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public long countCommentVideosByVideo(Integer idVideo) {
        return commentVideoRepository.countCommentVideosByVideo(idVideo);
    }

    public List<CommentVideoResponse> getAllComment(Integer idVideo) {
        return commentVideoRepository.findAllByVideo_IdVideoOrderByIdCommentVideoDesc(idVideo).stream()
                .map(commentVideoMapper::toCommentVideoResponse)
                .toList();
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
