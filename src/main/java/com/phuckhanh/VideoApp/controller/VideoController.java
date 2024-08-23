package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
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
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VideoController {
    VideoService videoService;

    @GetMapping("/all/by/channel/name_unique/{nameUniqueChannel}")
    ApiResponse<List<VideoResponse>> getAllByChannelNameUnique(@PathVariable String nameUniqueChannel) {
        return ApiResponse.<List<VideoResponse>>builder()
                .result(videoService.getAllByChannelNameUnique(nameUniqueChannel))
                .build();
    }

    @GetMapping("/count/all/by/channel/name_unique/{nameUniqueChannel}")
    ApiResponse<Long> countAllByChannelNameUnique(@PathVariable String nameUniqueChannel) {
        return ApiResponse.<Long>builder()
                .result(videoService.countAllByChannelNameUnique(nameUniqueChannel))
                .build();
    }

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
