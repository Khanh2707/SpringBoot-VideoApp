package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CommentInCommentCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentInCommentUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentInCommentUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.CommentInCommentResponse;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentInCommentResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.CommentInCommentMapper;
import com.phuckhanh.VideoApp.mapper.HistoryNotificationCommentInCommentMapper;
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
public class CommentInCommentService {
    CommentInCommentMapper commentInCommentMapper;
    ChannelRepository channelRepository;
    CommentVideoRepository commentVideoRepository;
    CommentInCommentRepository commentInCommentRepository;
    HistoryNotificationCommentInCommentRepository historyNotificationCommentInCommentRepository;
    HistoryNotificationCommentInCommentMapper historyNotificationCommentInCommentMapper;
    CheckHistoryNotificationCommentInCommentRepository checkHistoryNotificationCommentInCommentRepository;

    public long countHistoryNotificationCommentInCommentFromTimeToTime(Integer idChannel) {
        CheckHistoryNotificationCommentInComment checkHistoryNotificationCommentInComment = checkHistoryNotificationCommentInCommentRepository.findByChannel_IdChannel(idChannel).orElse(null);

        if (checkHistoryNotificationCommentInComment == null) {
            Channel channel = channelRepository.findById(idChannel)
                    .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

            checkHistoryNotificationCommentInComment = new CheckHistoryNotificationCommentInComment();
            checkHistoryNotificationCommentInComment.setDateTimeCheck(LocalDateTime.now());
            checkHistoryNotificationCommentInComment.setChannel(channel);

            checkHistoryNotificationCommentInCommentRepository.save(checkHistoryNotificationCommentInComment);

            Pageable pageable = PageRequest.of(0, 1);

            return historyNotificationCommentInCommentRepository.findAllByChannel_IdChannelOrderByCommentInCommentDesc(idChannel, pageable).getTotalElements();
        } else {
            LocalDateTime dateTimeCheck = checkHistoryNotificationCommentInComment.getDateTimeCheck();
            LocalDateTime now = LocalDateTime.now();

            List<HistoryNotificationCommentInComment> historyNotificationCommentInComments = historyNotificationCommentInCommentRepository.findAllByChannelIdAndTimeBetween(
                    idChannel, dateTimeCheck, now
            );

            return historyNotificationCommentInComments.size();
        }
    }

    public long countCommentByCommentVideo(Integer idCommentVideo) {
        return commentInCommentRepository.countCommentByCommentVideo(idCommentVideo);
    }

    public Page<HistoryNotificationCommentInCommentResponse> getAllNotificationCommentInComment(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyNotificationCommentInCommentRepository.findAllByChannel_IdChannelOrderByCommentInCommentDesc(idChannel, pageable)
                .map(historyNotificationCommentInCommentMapper::toHistoryNotificationCommentInCommentResponse);
    }

    public Page<CommentInCommentResponse> getAllCommentInComment(Integer idCommentVideo, String propertySort, String optionSort, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
        Pageable pageable = PageRequest.of(page, size, sort);

        return commentInCommentRepository.findAllByCommentVideo_IdCommentVideo(idCommentVideo, pageable)
                .map(commentInCommentMapper::toCommentInCommentResponse);
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

    public void updateCheckHistoryNotificationCommentInComment(Integer idChannel) {
        Optional<CheckHistoryNotificationCommentInComment> optionalCheckHistory = checkHistoryNotificationCommentInCommentRepository.findByChannel_IdChannel(idChannel);

        CheckHistoryNotificationCommentInComment checkHistoryNotificationCommentInComment;

        if (optionalCheckHistory.isPresent()) {
            checkHistoryNotificationCommentInComment = optionalCheckHistory.get();
        } else {
            checkHistoryNotificationCommentInComment = new CheckHistoryNotificationCommentInComment();
            Channel channel = channelRepository.findById(idChannel).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
            checkHistoryNotificationCommentInComment.setChannel(channel);
        }

        checkHistoryNotificationCommentInComment.setDateTimeCheck(LocalDateTime.now());

        checkHistoryNotificationCommentInCommentRepository.save(checkHistoryNotificationCommentInComment);
    }

    public HistoryNotificationCommentInCommentResponse updateIsCheckHistoryNotificationCommentInComment(Integer idChannel, Integer idNotificationCommentInComment, HistoryNotificationCommentInCommentUpdateRequest request) {
        HistoryNotificationCommentInCommentKey historyNotificationCommentInCommentKey = new HistoryNotificationCommentInCommentKey(idNotificationCommentInComment, idChannel);
        HistoryNotificationCommentInComment historyNotificationCommentInComment = historyNotificationCommentInCommentRepository.findByIdHistoryNotificationCommentInCommentKey(historyNotificationCommentInCommentKey).orElseThrow(() -> new AppException(ErrorCode.HISTORY_NOTIFICATION_COMMENT_COMMENT_NOT_FOUND));

        historyNotificationCommentInCommentMapper.updateHistoryNotificationCommentInCommentIsCheck(historyNotificationCommentInComment, request);

        return historyNotificationCommentInCommentMapper.toHistoryNotificationCommentInCommentResponse(historyNotificationCommentInCommentRepository.save(historyNotificationCommentInComment));
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
