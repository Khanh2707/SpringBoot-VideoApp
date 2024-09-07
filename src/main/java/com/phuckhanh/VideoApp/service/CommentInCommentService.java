package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CommentInCommentCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentInCommentUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.CommentInCommentResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.CommentInCommentMapper;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.CommentInCommentRepository;
import com.phuckhanh.VideoApp.repository.CommentVideoRepository;
import com.phuckhanh.VideoApp.repository.HistoryNotificationCommentInCommentRepository;
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
public class CommentInCommentService {
    CommentInCommentMapper commentInCommentMapper;
    ChannelRepository channelRepository;
    CommentVideoRepository commentVideoRepository;
    CommentInCommentRepository commentInCommentRepository;
    HistoryNotificationCommentInCommentRepository historyNotificationCommentInCommentRepository;

    public long countCommentByCommentVideo(Integer idCommentVideo) {
        return commentInCommentRepository.countCommentByCommentVideo(idCommentVideo);
    }

    public List<CommentInCommentResponse> getAllCommentComment(Integer idCommentVideo) {
        return commentInCommentRepository.findAllByCommentVideo_IdCommentVideoOrderByIdCommentInCommentDesc(idCommentVideo).stream()
                .map(commentInCommentMapper::toCommentInCommentResponse)
                .toList();
    }

    public CommentInCommentResponse createCommentInComment(CommentInCommentCreationRequest request) {
        CommentInComment commentInComment = commentInCommentMapper.toCommentInComment(request);

        commentInComment.setDateTimeComment(LocalDateTime.now());

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        CommentVideo commentVideo = commentVideoRepository.findById(request.getIdCommentVideo()).orElseThrow(() -> new AppException(ErrorCode.COMMENT_VIDEO_NOT_FOUND));

        commentInComment.setChannel(channel);
        commentInComment.setCommentVideo(commentVideo);

        commentInCommentRepository.save(commentInComment);

        HistoryNotificationCommentInComment historyNotificationCommentInComment = new HistoryNotificationCommentInComment();
        historyNotificationCommentInComment.setIdHistoryNotificationCommentInCommentKey(new HistoryNotificationCommentInCommentKey(commentInComment.getIdCommentInComment(), commentVideo.getChannel().getIdChannel()));
        historyNotificationCommentInComment.setCommentInComment(commentInComment);
        historyNotificationCommentInComment.setChannel(commentVideo.getChannel());
        historyNotificationCommentInComment.setIsCheck(false);
        historyNotificationCommentInCommentRepository.save(historyNotificationCommentInComment);

        return commentInCommentMapper.toCommentInCommentResponse(commentInComment);
    }

    public CommentInCommentResponse updateCommentCommentContent(Integer idCommentInComment, CommentInCommentUpdateContentRequest request) {
        CommentInComment commentInComment = commentInCommentRepository.findById(idCommentInComment).orElseThrow(() -> new AppException(ErrorCode.COMMENT_COMMENT_NOT_FOUND));

        commentInCommentMapper.updateCommentCommentContent(commentInComment, request);

        commentInComment.setDateTimeComment(LocalDateTime.now());

        return commentInCommentMapper.toCommentInCommentResponse(commentInCommentRepository.save(commentInComment));
    }

    public void deleteCommentComment(Integer idCommentInComment) {
        CommentInComment commentInComment = commentInCommentRepository.findById(idCommentInComment).orElseThrow(() -> new AppException(ErrorCode.COMMENT_COMMENT_NOT_FOUND));

        commentInCommentRepository.delete(commentInComment);
    }
}
