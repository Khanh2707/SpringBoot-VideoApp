package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CommentInCommentCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentInCommentUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CommentInCommentResponse;
import com.phuckhanh.VideoApp.service.CommentInCommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/by/comment_video/{idCommentVideo}")
    public ApiResponse<List<CommentInCommentResponse>> getAllCommentComment(@PathVariable Integer idCommentVideo) {
        return ApiResponse.<List<CommentInCommentResponse>>builder()
                .result(commentInCommentService.getAllCommentComment(idCommentVideo))
                .build();
    }

    @PostMapping("")
    public ApiResponse<CommentInCommentResponse> createCommentInComment(@RequestBody CommentInCommentCreationRequest request) {
        return ApiResponse.<CommentInCommentResponse>builder()
                .result(commentInCommentService.createCommentInComment(request))
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
