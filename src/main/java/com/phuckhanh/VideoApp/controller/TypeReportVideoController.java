package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.TypeReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.TypeReportVideoResponse;
import com.phuckhanh.VideoApp.service.TypeReportVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/type_report_videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TypeReportVideoController {
    TypeReportVideoService typeReportVideoService;

    @GetMapping("/pageable/{page}/{size}")
    public ApiResponse<Page<TypeReportVideoResponse>> getAllTypeReportVideos(@PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<TypeReportVideoResponse>>builder()
                .result(typeReportVideoService.getAllTypeReportVideos(page, size))
                .build();
    }

    @PostMapping("")
    public ApiResponse<TypeReportVideoResponse> createTypeReportVideo(@RequestBody TypeReportVideoCreationRequest request) {
        return ApiResponse.<TypeReportVideoResponse>builder()
                .result(typeReportVideoService.createTypeReportVideo(request))
                .build();
    }
}
