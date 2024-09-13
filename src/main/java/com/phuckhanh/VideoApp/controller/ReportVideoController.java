package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.ReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.ReportVideoResponse;
import com.phuckhanh.VideoApp.service.ReportVideoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report_videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportVideoController {
    ReportVideoService reportVideoService;

    @GetMapping("{propertySort}/{optionSort}/pageable/{page}/{size}/typeReportVideo/{idTypeReportVideo}")
    public ApiResponse<Page<ReportVideoResponse>> getAllReportVideos(@PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer idTypeReportVideo) {
        return ApiResponse.<Page<ReportVideoResponse>>builder()
                .result(reportVideoService.getAllReportVideos(propertySort, optionSort, page, size, idTypeReportVideo))
                .build();
    }

    @PostMapping("")
    public ApiResponse<ReportVideoResponse> createReportVideo(@RequestBody ReportVideoCreationRequest request) {
        return ApiResponse.<ReportVideoResponse>builder()
                .result(reportVideoService.createReportVideo(request))
                .build();
    }
}
