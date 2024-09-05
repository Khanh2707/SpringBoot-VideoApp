package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.WebSocketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification")
    public void sendNotification(@Payload WebSocketRequest webSocketRequest) {
        simpMessagingTemplate.convertAndSend("/notification", webSocketRequest);
    }
}
