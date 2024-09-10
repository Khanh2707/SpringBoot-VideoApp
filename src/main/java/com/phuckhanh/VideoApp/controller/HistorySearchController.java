package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.HistorySearchCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.HistorySearchResponse;
import com.phuckhanh.VideoApp.service.HistorySearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history_searched")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HistorySearchController {
    HistorySearchService historySearchService;

    @GetMapping("/{idChannel}/{page}/{size}")
    public ApiResponse<Page<HistorySearchResponse>> getAllHistorySearchByChannel(@PathVariable Integer idChannel, @PathVariable Integer page, @PathVariable Integer size) {
        return ApiResponse.<Page<HistorySearchResponse>>builder()
                .result(historySearchService.getAllHistorySearchByChannel(idChannel, page, size))
                .build();
    }

    @PostMapping("")
    public ApiResponse<HistorySearchResponse> createHistorySearch(@RequestBody HistorySearchCreationRequest request) {
        return ApiResponse.<HistorySearchResponse>builder()
                .result(historySearchService.createHistorySearch(request))
                .build();
    }

    @DeleteMapping("/{idHistorySearch}")
    public ApiResponse<String> deleteHistorySearch(@PathVariable Integer idHistorySearch) {
        historySearchService.deleteHistorySearch(idHistorySearch);
        String resultMessage = String.format("Delete history search %d success", idHistorySearch);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
