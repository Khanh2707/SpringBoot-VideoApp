package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.BanChannelCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.BanChannelResponse;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.service.BanChannelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ban_channels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BanChannelController {
    private final BanChannelService banChannelService;

    @GetMapping("/channel/{idChannel}")
    public ApiResponse<BanChannelResponse> getChannelIsBan(@PathVariable Integer idChannel) {
        return ApiResponse.<BanChannelResponse>builder()
                .result(banChannelService.getChannelIsBan(idChannel))
                .build();
    }

    @PostMapping("")
    public ApiResponse<BanChannelResponse> createBanChannel(@RequestBody BanChannelCreationRequest request) {
        return ApiResponse.<BanChannelResponse>builder()
                .result(banChannelService.createBanChannel(request))
                .build();
    }
}
