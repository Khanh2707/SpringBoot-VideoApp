package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.HistoryLikeVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.service.VideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VideoController {
    VideoService videoService;

    @GetMapping("/count/like/{idVideo}")
    public ApiResponse<Long> countChannelLikeVideo(@PathVariable Integer idVideo) {
        return ApiResponse.<Long>builder()
                .result(videoService.countChannelLikeVideo(idVideo))
                .build();
    }

    @GetMapping("/is/like/{idChannel}/{idVideo}")
    public ApiResponse<Boolean> isChannelLikeVideo(@PathVariable Integer idChannel, @PathVariable Integer idVideo) {
        return ApiResponse.<Boolean>builder()
                .result(videoService.isChannelLikeVideo(idChannel, idVideo))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<VideoResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.getById(id))
                .build();
    }

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

    @PostMapping("/like")
    ApiResponse<String> createHistoryLikeVideo(@RequestBody HistoryLikeVideoCreationRequest request) {
        videoService.createHistoryLikeVideo(request);
        String resultMessage = String.format("Channel %d like Video %d success", request.getIdChannel(), request.getIdVideo());
        return ApiResponse.<String>builder()
                .result(resultMessage)
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

    @DeleteMapping("/like/{idChannel}/{idVideo}")
    ApiResponse<String> deleteHistoryLikeVideo(@PathVariable Integer idChannel, @PathVariable Integer idVideo) {
        videoService.deleteHistoryLikeVideo(idChannel, idVideo);
        String resultMessage = String.format("Channel %d unlike Video %d success", idChannel, idVideo);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
