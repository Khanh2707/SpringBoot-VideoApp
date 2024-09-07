package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CommentVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.CommentVideoUpdateContentRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CommentVideoResponse;
import com.phuckhanh.VideoApp.service.CommentVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/by/video/{idVideo}")
    public ApiResponse<List<CommentVideoResponse>> getAllComment(@PathVariable Integer idVideo) {
        return ApiResponse.<List<CommentVideoResponse>>builder()
                .result(commentVideoService.getAllComment(idVideo))
                .build();
    }

    @PostMapping("")
    public ApiResponse<CommentVideoResponse> createCommentVideo(@RequestBody CommentVideoCreationRequest request) {
        return ApiResponse.<CommentVideoResponse>builder()
                .result(commentVideoService.createCommentVideo(request))
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
