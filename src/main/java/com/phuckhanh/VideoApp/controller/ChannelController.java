package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.ChannelSubChannelRequest;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.entity.ChannelSubChannel;
import com.phuckhanh.VideoApp.service.ChannelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChannelController {
    ChannelService channelService;

    @GetMapping("/count/sub/{idChannel2}")
    public ApiResponse<Long> countSubChannel(@PathVariable Integer idChannel2) {
        return ApiResponse.<Long>builder()
                .result(channelService.countUser1SubForUser2(idChannel2))
                .build();
    }

    @GetMapping("/is/sub/{idChannel1}/{idChannel2}")
    public ApiResponse<Boolean> checkChannelSubChannel(@PathVariable Integer idChannel1, @PathVariable Integer idChannel2) {
        return ApiResponse.<Boolean>builder()
                .result(channelService.isUser1SubscribingUser2(idChannel1, idChannel2))
                .build();
    }

    @GetMapping("/all/sub/{idChannel2}")
    public ApiResponse<List<ChannelResponse>> getAllSubChannel(@PathVariable Integer idChannel2) {
        return ApiResponse.<List<ChannelResponse>>builder()
                .result(channelService.getAllSubChannel(idChannel2))
                .build();
    }

    @PostMapping("/sub")
    ApiResponse<String> createChannelSubChannel(@RequestBody ChannelSubChannelRequest request) {
        channelService.createChannelSubChannel(request);
        String resultMessage = String.format("Channel %d subscribe Channel %d success", request.getIdChannel1(), request.getIdChannel2());
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }

    @DeleteMapping("/sub/{idChannel1}/{idChannel2}")
    ApiResponse<String> deleteChannelSubChannel(@PathVariable Integer idChannel1, @PathVariable Integer idChannel2) {
        channelService.deleteChannelSubChannel(idChannel1, idChannel2);
        String resultMessage = String.format("Channel %d unsubscribe Channel %d success", idChannel1, idChannel2);
        return ApiResponse.<String>builder()
                .result(resultMessage)
                .build();
    }
}
