package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.*;
import com.phuckhanh.VideoApp.dto.response.*;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInComment;
import com.phuckhanh.VideoApp.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VideoController {
    VideoService videoService;

    @GetMapping("/download/{idVideo}")
    public void downloadVideo(@PathVariable("idVideo") Integer idVideo, HttpServletResponse response) throws IOException {
        videoService.downloadVideo(idVideo, response);
    }

    @GetMapping("/count/history_notification_video/from_time_to_time/{idChannel}")
    public ApiResponse<Long> countHistoryNotificationVideoFromTimeToTime(@PathVariable Integer idChannel) {
        return ApiResponse.<Long>builder()
                .result(videoService.countHistoryNotificationVideoFromTimeToTime(idChannel))
                .build();
    }

    @GetMapping("/count/like/{idVideo}")
    public ApiResponse<Long> countChannelLikeVideo(@PathVariable Integer idVideo) {
        return ApiResponse.<Long>builder()
                .result(videoService.countChannelLikeVideo(idVideo))
                .build();
    }

    @GetMapping("/count/all/by/channel/name_unique/{nameUniqueChannel}")
    public ApiResponse<Long> countAllByChannelNameUnique(@PathVariable String nameUniqueChannel) {
        return ApiResponse.<Long>builder()
                .result(videoService.countAllByChannelNameUnique(nameUniqueChannel))
                .build();
    }

    @GetMapping("/is/like/{idChannel}/{idVideo}")
    public ApiResponse<Boolean> isChannelLikeVideo(@PathVariable Integer idChannel, @PathVariable Integer idVideo) {
        return ApiResponse.<Boolean>builder()
                .result(videoService.isChannelLikeVideo(idChannel, idVideo))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VideoResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.getById(id))
                .build();
    }

    @GetMapping("/all/notification/video/{idChannel}/pageable/{page}/{size}")
    public ApiResponse<Page<HistoryNotificationVideoResponse>> getAllNotificationCreateVideo(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<HistoryNotificationVideoResponse>>builder()
                .result(videoService.getAllNotificationCreateVideo(idChannel, page, size))
                .build();
    }

    @GetMapping("/search/all/video/channel/watched/{idChannel}/{keyword}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> searchWatchedVideosByChannelAndTitle(@PathVariable Integer idChannel, @PathVariable String keyword, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.searchWatchedVideosByChannelAndTitle(idChannel, keyword, page, size))
                .build();
    }

    @GetMapping("/all/video/channel/watched/{idChannel}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> getAllVideoChannelWatched(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getAllVideoChannelWatched(idChannel, page, size))
                .build();
    }

    @GetMapping("/search/all/video/channel/liked/{idChannel}/{keyword}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> searchLikedVideosByChannelAndTitle(@PathVariable Integer idChannel, @PathVariable String keyword, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.searchLikedVideosByChannelAndTitle(idChannel, keyword, page, size))
                .build();
    }

    @GetMapping("/all/video/channel/liked/{idChannel}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> getAllVideoChannelLiked(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getAllVideoChannelLiked(idChannel, page, size))
                .build();
    }

    @GetMapping("/search/all/video/channel/{nameUnique}/{keyword}/{propertySort}/{optionSort}/pageable/{page}/{size}/category/{idCategory}")
    public ApiResponse<Page<VideoResponse>> searchVideosByChannelAndTitle(@PathVariable String nameUnique, @PathVariable String keyword, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer idCategory) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.searchVideosByChannelAndTitle(nameUnique, keyword, propertySort, optionSort, page, size, idCategory))
                .build();
    }

    @GetMapping("/all/by/channel/name_unique/{nameUniqueChannel}/{propertySort}/{optionSort}/pageable/{page}/{size}/category/{idCategory}")
    ApiResponse<Page<VideoResponse>> getAllByChannelNameUnique(@PathVariable String nameUniqueChannel, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer idCategory) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getAllByChannelNameUnique(nameUniqueChannel, propertySort, optionSort, page, size, idCategory))
                .build();
    }

    @GetMapping("/{propertySort}/{optionSort}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> getAllVideo(@PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getAllVideo(propertySort, optionSort, page, size))
                .build();
    }

    @GetMapping("/all/by/category/{idCategory}/{propertySort}/{optionSort}/pageable/{page}/{size}")
    public ApiResponse<Page<VideoResponse>> getAllVideoByCategory(@PathVariable Integer idCategory, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getAllVideoByCategory(idCategory, propertySort, optionSort, page, size))
                .build();
    }

    @PostMapping("/watch")
    public ApiResponse<String> createHistoryWatchVideo(@RequestBody HistoryWatchVideoCreationRequest request) {
        videoService.createHistoryWatchVideo(request);
        String resultMessage = String.format("Channel %d watch Video %d success", request.getIdChannel(), request.getIdVideo());
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }

    @PostMapping("/like")
    public ApiResponse<String> createHistoryLikeVideo(@RequestBody HistoryLikeVideoCreationRequest request) {
        videoService.createHistoryLikeVideo(request);
        String resultMessage = String.format("Channel %d like Video %d success", request.getIdChannel(), request.getIdVideo());
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }

    @PostMapping("/check/history/notification/video/{idChannel}")
    public ApiResponse<String> updateCheckHistoryNotificationVideo(@PathVariable Integer idChannel) {
        videoService.updateCheckHistoryNotificationVideo(idChannel);
        return ApiResponse.<String>builder()
                .result("Update check notification video success!")
                .build();
    }

    @PostMapping("")
    public ApiResponse<VideoResponse> createVideo(@ModelAttribute VideoCreationRequest request) throws IOException {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.createVideo(request))
                .build();
    }

    @PutMapping("/{idVideo}")
    public ApiResponse<VideoResponse> updateVideo(@PathVariable Integer idVideo, @RequestBody VideoUpdateRequest request) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.updateVideo(idVideo, request))
                .build();
    }

    @PutMapping("view/{idVideo}")
    public ApiResponse<VideoResponse> updateViewVideo(@PathVariable Integer idVideo) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.updateViewVideo(idVideo))
                .build();
    }

    @PutMapping("/is_check/history/notification/video/{idChannel}/{idNotificationVideo}")
    public ApiResponse<HistoryNotificationVideoResponse> updateIsCheckHistoryNotificationVideo(@PathVariable Integer idChannel, @PathVariable Integer idNotificationVideo, @RequestBody HistoryNotificationVideoUpdateRequest request) {
        return ApiResponse.<HistoryNotificationVideoResponse>builder()
                .result(videoService.updateIsCheckHistoryNotificationVideo(idChannel, idNotificationVideo, request))
                .build();
    }

    @DeleteMapping("/{idVideo}")
    public ApiResponse<String> deleteVideo(@PathVariable Integer idVideo) throws IOException {
        videoService.deleteVideo(idVideo);
        return ApiResponse.<String>builder()
                .result("Video has been deleted")
                .build();
    }

    @DeleteMapping("/like/{idChannel}/{idVideo}")
    public ApiResponse<String> deleteHistoryLikeVideo(@PathVariable Integer idChannel, @PathVariable Integer idVideo) {
        videoService.deleteHistoryLikeVideo(idChannel, idVideo);
        String resultMessage = String.format("Channel %d unlike Video %d success", idChannel, idVideo);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }

    @DeleteMapping("/all/watch/{idChannel}")
    public ApiResponse<String> deleteHistoryWatchVideo(@PathVariable Integer idChannel) {
        videoService.deleteAllHistoryWatchVideosByChannel(idChannel);
        String resultMessage = String.format("Channel %d delete all watched Video", idChannel);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
