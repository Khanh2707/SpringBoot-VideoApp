package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CommentVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentVideoUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentVideoUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CommentVideoResponse;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentVideoResponse;
import com.phuckhanh.VideoApp.service.CommentVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment_videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentVideoController {
    CommentVideoService commentVideoService;

    @GetMapping("/count/by/video/{idVideo}")
    public ApiResponse<Long> countCommentVideosByVideo(@PathVariable Integer idVideo) {
        return ApiResponse.<Long>builder()
                .result(commentVideoService.countCommentVideosByVideo(idVideo))
                .build();
    }

    @GetMapping("/count/history_notification_comment_video/from_time_to_time/{idChannel}")
    public ApiResponse<Long> countHistoryNotificationCommentVideoFromTimeToTime(@PathVariable Integer idChannel) {
        return ApiResponse.<Long>builder()
                .result(commentVideoService.countHistoryNotificationCommentVideoFromTimeToTime(idChannel))
                .build();
    }

    @GetMapping("/all/notification/comment_video/{idChannel}/pageable/{page}/{size}")
    ApiResponse<Page<HistoryNotificationCommentVideoResponse>> getAllNotificationCommentVideo(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<HistoryNotificationCommentVideoResponse>>builder()
                .result(commentVideoService.getAllNotificationCommentVideo(idChannel, page, size))
                .build();
    }

    @GetMapping("/by/video/{idVideo}/{propertySort}/{optionSort}/pageable/{page}/{size}")
    public ApiResponse<Page<CommentVideoResponse>> getAllComment(@PathVariable Integer idVideo, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<CommentVideoResponse>>builder()
                .result(commentVideoService.getAllComment(idVideo, propertySort, optionSort, page, size))
                .build();
    }

    @PostMapping("")
    public ApiResponse<CommentVideoResponse> createCommentVideo(@RequestBody CommentVideoCreationRequest request) {
        return ApiResponse.<CommentVideoResponse>builder()
                .result(commentVideoService.createCommentVideo(request))
                .build();
    }

    @PostMapping("/check/history/notification/comment_video/{idChannel}")
    ApiResponse<String> updateCheckHistoryNotificationCommentVideo(@PathVariable Integer idChannel) {
        commentVideoService.updateCheckHistoryNotificationCommentVideo(idChannel);
        return ApiResponse.<String>builder()
                .result("Update check notification comment video success!")
                .build();
    }

    @PutMapping("/is_check/history/notification/comment_video/{idChannel}/{idNotificationCommentVideo}")
    ApiResponse<HistoryNotificationCommentVideoResponse> updateIsCheckHistoryNotificationCommentVideo(@PathVariable Integer idChannel, @PathVariable Integer idNotificationCommentVideo, @RequestBody HistoryNotificationCommentVideoUpdateRequest request) {
        return ApiResponse.<HistoryNotificationCommentVideoResponse>builder()
                .result(commentVideoService.updateIsCheckHistoryNotificationCommentVideo(idChannel, idNotificationCommentVideo, request))
                .build();
    }

    @PutMapping("/content/{idCommentVideo}")
    public ApiResponse<CommentVideoResponse> updateCommentVideoContent(@PathVariable Integer idCommentVideo, @RequestBody CommentVideoUpdateContentRequest request) {
        return ApiResponse.<CommentVideoResponse>builder()
                .result(commentVideoService.updateCommentVideoContent(idCommentVideo, request))
                .build();
    }

    @DeleteMapping("/{idCommentVideo}")
    public ApiResponse<String> deleteCommentVideo(@PathVariable Integer idCommentVideo) {
        commentVideoService.deleteCommentVideo(idCommentVideo);
        String resultMessage = String.format("Delete comment video %d", idCommentVideo);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
