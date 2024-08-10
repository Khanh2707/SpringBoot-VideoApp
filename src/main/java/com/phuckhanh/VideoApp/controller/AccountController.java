package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.AccountCreationRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountController {
    AccountService accountService;

    @GetMapping("/myAccount")
    ApiResponse<AccountResponse> getMyAccount() {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.getMyAccount())
                .build();
    }

    @GetMapping("/by_channel/{nameChannel}")
    ApiResponse<List<AccountResponse>> getListAccountByNameChannel(@PathVariable String nameChannel) {
        return ApiResponse.<List<AccountResponse>>builder()
                .result(accountService.getListAccountByNameChannel(nameChannel))
                .build();
    }

    @PostMapping("")
    ApiResponse<AccountResponse> createAccount(@RequestBody AccountCreationRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.createAccount(request))
                .build();
    }

    @DeleteMapping("/{username}")
    ApiResponse<String> deleteAccountByUsername(@PathVariable String username) {
        accountService.deleteAccountByUsername(username);
        return ApiResponse.<String>builder()
                .result("Account has been deleted")
                .build();
    }
}
