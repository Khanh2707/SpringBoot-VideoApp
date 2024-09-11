package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CommentInCommentCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentInCommentUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.request.HistoryNotificationCommentInCommentUpdateRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CommentInCommentResponse;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationCommentInCommentResponse;
import com.phuckhanh.VideoApp.service.CommentInCommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment_comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentInCommentController {
    CommentInCommentService commentInCommentService;

    @GetMapping("/count/by/comment_video/{idCommentVideo}")
    public ApiResponse<Long> countCommentByCommentVideo(@PathVariable Integer idCommentVideo) {
        return ApiResponse.<Long>builder()
                .result(commentInCommentService.countCommentByCommentVideo(idCommentVideo))
                .build();
    }

    @GetMapping("/count/history_notification_comment_comment/from_time_to_time/{idChannel}")
    public ApiResponse<Long> countHistoryNotificationCommentInCommentFromTimeToTime(@PathVariable Integer idChannel) {
        return ApiResponse.<Long>builder()
                .result(commentInCommentService.countHistoryNotificationCommentInCommentFromTimeToTime(idChannel))
                .build();
    }

    @GetMapping("/all/notification/comment_comment/{idChannel}/pageable/{page}/{size}")
    ApiResponse<Page<HistoryNotificationCommentInCommentResponse>> getAllNotificationCommentInComment(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<HistoryNotificationCommentInCommentResponse>>builder()
                .result(commentInCommentService.getAllNotificationCommentInComment(idChannel, page, size))
                .build();
    }

    @GetMapping("/by/comment_video/{idCommentVideo}/{propertySort}/{optionSort}/pageable/{page}/{size}")
    public ApiResponse<Page<CommentInCommentResponse>> getAllCommentComment(@PathVariable Integer idCommentVideo, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<CommentInCommentResponse>>builder()
                .result(commentInCommentService.getAllCommentInComment(idCommentVideo, propertySort, optionSort, page, size))
                .build();
    }

    @PostMapping("")
    public ApiResponse<CommentInCommentResponse> createCommentInComment(@RequestBody CommentInCommentCreationRequest request) {
        return ApiResponse.<CommentInCommentResponse>builder()
                .result(commentInCommentService.createCommentInComment(request))
                .build();
    }

    @PostMapping("/check/history/notification/comment_comment/{idChannel}")
    ApiResponse<String> updateCheckHistoryNotificationCommentVideo(@PathVariable Integer idChannel) {
        commentInCommentService.updateCheckHistoryNotificationCommentInComment(idChannel);
        return ApiResponse.<String>builder()
                .result("Update check notification comment comment success!")
                .build();
    }

    @PutMapping("/is_check/history/notification/comment_comment/{idChannel}/{idNotificationCommentInComment}")
    ApiResponse<HistoryNotificationCommentInCommentResponse> updateIsCheckHistoryNotificationCommentInComment(@PathVariable Integer idChannel, @PathVariable Integer idNotificationCommentInComment, @RequestBody HistoryNotificationCommentInCommentUpdateRequest request) {
        return ApiResponse.<HistoryNotificationCommentInCommentResponse>builder()
                .result(commentInCommentService.updateIsCheckHistoryNotificationCommentInComment(idChannel, idNotificationCommentInComment, request))
                .build();
    }

    @PutMapping("/content/{idCommentInComment}")
    public ApiResponse<CommentInCommentResponse> updateCommentCommentContent(@PathVariable Integer idCommentInComment, @RequestBody CommentInCommentUpdateContentRequest request) {
        return ApiResponse.<CommentInCommentResponse>builder()
                .result(commentInCommentService.updateCommentCommentContent(idCommentInComment, request))
                .build();
    }

    @DeleteMapping("/{idCommentInComment}")
    public ApiResponse<String> deleteCommentComment(@PathVariable Integer idCommentInComment) {
        commentInCommentService.deleteCommentComment(idCommentInComment);
        String resultMessage = String.format("Delete comment comment %d", idCommentInComment);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
