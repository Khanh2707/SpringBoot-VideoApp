package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.CategoryResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.service.VideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VideoController {
    VideoService videoService;

    @PostMapping("")
    ApiResponse<VideoResponse> createVideo(@ModelAttribute VideoCreationRequest request) throws IOException {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.createVideo(request))
                .build();
    }

    @DeleteMapping("/{idVideo}")
    ApiResponse<String> deleteVideo(@PathVariable Integer idVideo) throws IOException {
        videoService.deleteVideo(idVideo);
        return ApiResponse.<String>builder()
                .result("Video has been deleted")
                .build();
    }
}
