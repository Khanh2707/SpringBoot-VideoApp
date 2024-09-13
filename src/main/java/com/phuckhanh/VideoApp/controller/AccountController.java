package com.phuckhanh.VideoApp.controller;

import com.phuckhanh.VideoApp.dto.request.AccountCreationRequest;
import com.phuckhanh.VideoApp.dto.request.AccountUpdatePasswordRequest;
import com.phuckhanh.VideoApp.dto.request.AccountUpdateRoleRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.dto.response.ApiResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ApiResponse<AccountResponse> getMyAccount() {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.getMyAccount())
                .build();
    }

    @GetMapping("/by/channel/name_unique/{nameUniqueChannel}")
    public ApiResponse<AccountResponse> getAccountByChannelNameUnique(@PathVariable String nameUniqueChannel) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.getAccountByChannelNameUnique(nameUniqueChannel))
                .build();
    }

    @GetMapping("/search/all/account/{keyword}/{propertySort}/{optionSort}/pageable/{page}/{size}/role/{idRole}")
    public ApiResponse<Page<AccountResponse>> searchAccountsChannelName(@PathVariable String keyword, @PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer idRole) {
        return ApiResponse.<Page<AccountResponse>>builder()
                .result(accountService.searchAccountsByAccountChannelName(keyword, propertySort, optionSort, page, size, idRole))
                .build();
    }

    @GetMapping("/{propertySort}/{optionSort}/pageable/{page}/{size}/role/{idRole}")
    public ApiResponse<Page<AccountResponse>> getAllAccount(@PathVariable String propertySort, @PathVariable String optionSort, @PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer idRole) {
        return ApiResponse.<Page<AccountResponse>>builder()
                .result(accountService.getAllAccount(propertySort, optionSort, page, size, idRole))
                .build();
    }

    @PostMapping("")
    public ApiResponse<AccountResponse> createAccount(@RequestBody AccountCreationRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.createAccount(request))
                .build();
    }

    @PutMapping("/role/{username}")
    public ApiResponse<AccountResponse> updateAccountRole(@PathVariable String username, @RequestBody AccountUpdateRoleRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.updateAccountRole(username, request))
                .build();
    }

    @PutMapping("/password/{username}")
    public ApiResponse<AccountResponse> updateAccountPassword(@PathVariable String username, @RequestBody AccountUpdatePasswordRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.updateAccountPassword(username, request))
                .build();
    }

    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteAccountByUsername(@PathVariable String username) {
        accountService.deleteAccountByUsername(username);
        return ApiResponse.<String>builder()
                .result("Account has been deleted")
                .build();
    }
}
