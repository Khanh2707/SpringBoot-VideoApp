package com.phuckhanh.VideoApp.repository.httpclient;

import com.phuckhanh.VideoApp.dto.request.ExchangeTokenRequest;
import com.phuckhanh.VideoApp.dto.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "outbound-videoapp", url = "https://oauth2.googleapis.com")
public interface OutboundVideoAppClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}
